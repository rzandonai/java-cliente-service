package br.com.rzandonai.client;

import br.com.rzandonai.client.configs.SpecificationConfig;
import br.com.rzandonai.client.entities.Cliente;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.lettuce.core.RedisURI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@ImportAutoConfiguration(classes = {CacheAutoConfiguration.class, RedisAutoConfiguration.class, SpecificationConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ClientApplicationTests {

    @Test
    void contextLoads() {
    }

    private final static String BASE_URL = "/clientes";

    @Autowired
    private MockMvc mockMvc;

    Gson gson = new Gson();

    @Test
    public void testCriarCliente() throws Exception {
        createCliente("Ricardo");
    }

    @Test
    public void testCriarClienteBuscaPorNome() throws Exception {
        String nome = "Pedro";
        createCliente(nome);
        buscarCliente(nome);
    }

    @Test
    public void testCriarClienteBuscaPorNomeAtualizarCEP() throws Exception {
        String nome = "Leonardo";
        createCliente(nome);
        Cliente cliente = buscarCliente(nome);
        assertNotNull(cliente);
        String cep = "89056-310";
        cliente.setCep(cep);
        assertEquals(atualizarCliente(cliente).getCep(), cep);
    }


    @Test
    public void testCriarClienteDeletarBuscarNovamente() throws Exception {
        String nome = "Rafael";
        Cliente cliente = createCliente(nome);
        assertNotNull(cliente);
        deletarClient(cliente);
    }

    private void deletarClient(Cliente cliente) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete(
                        String.format("%s/%s", BASE_URL, cliente.getId()))


                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private Cliente createCliente(String nome) throws Exception {
        Cliente cliente = mockCliente(nome);
        String json = gson.toJson(cliente);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        return gson.fromJson(result.getResponse().getContentAsString(), Cliente.class);
    }

    private Cliente mockCliente(String nome) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf("013.284.300-57");
        cliente.setRua("Rua do teste");
        cliente.setNumero(123);
        cliente.setComplemento("casa");
        cliente.setCidade("Testeville");
        cliente.setCidade("41305-250");
        cliente.setPais("Brasil");
        return cliente;
    }


    private Cliente buscarCliente(String nome) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("search", String.format("%s:%s", "nome", nome))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        return gson.fromJson(new Gson().fromJson(result.getResponse().getContentAsString(), JsonObject.class).getAsJsonObject().get("content").getAsJsonArray().get(0).toString(), Cliente.class);

    }


    private Cliente atualizarCliente(Cliente cliente) throws Exception {
        String json = gson.toJson(cliente);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        return gson.fromJson(result.getResponse().getContentAsString(), Cliente.class);
    }


    @TestConfiguration
    static class EmbeddedRedisConfiguration {
        private final RedisServer redisServer;

        public EmbeddedRedisConfiguration() {
            this.redisServer = new RedisServer(5432);
            System.setProperty("REDIS_URL", RedisURI.Builder.redis("localhost", 5432).build().toString());
        }

        @PostConstruct
        public void startRedis() {
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            this.redisServer.stop();
        }
    }

}
