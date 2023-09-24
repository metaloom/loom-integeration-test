package io.metaloom.loom.test.integration;

import org.junit.jupiter.api.Test;

import io.metaloom.loom.api.Loom;
import io.metaloom.loom.client.http.LoomHttpClient;
import io.metaloom.loom.rest.model.asset.AssetListResponse;
import io.metaloom.loom.test.TestEnvHelper;
import io.metaloom.loom.test.Testdata;

public class BasicIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testIntegration() throws Exception {
		// 1. Start server
		Loom server = loomServer();
		server.run(false);

		// 2. Setup fresh test data
		Testdata data = TestEnvHelper.prepareTestdata("integration-test");
		String path = data.root().getAbsolutePath();

		// 3. Invoke the CLI
		loomCortex("p",  "analyze", path);

		try (LoomHttpClient client = httpClient(server)) {
			loginAdmin(client);
			AssetListResponse assets = client.listAssets().sync();
			System.out.println("Assets: " + assets.getData().size());
		}
		server.shutdown();
	}

	

}
