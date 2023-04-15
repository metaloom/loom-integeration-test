package io.metaloom.loom.test.integration;

import org.junit.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.metaloom.loom.api.Loom;
import io.metaloom.loom.api.options.LoomOptions;
import io.metaloom.loom.cortex.cli.LoomCortexCLI;
import io.metaloom.loom.test.LoomProviderExtension;
import io.metaloom.loom.test.TestEnvHelper;
import io.metaloom.loom.test.Testdata;

public class BasicIntegrationTest {

	@RegisterExtension
	public static LoomProviderExtension provider = LoomProviderExtension.create();

	@Test
	public void testIntegration() throws Exception {
		LoomOptions options = new LoomOptions();
		options.setDatabase(provider.options());

		Loom loom = Loom.create(options);
		loom.run(false);

		Testdata data = TestEnvHelper.prepareTestdata("integration-test");
		String path = data.root().getAbsolutePath();
		LoomCortexCLI.execute("--hostname", "localhost",
			"--port", String.valueOf(options.getServer().getGrpcPort()),
			"p", "analyze", path);
		loom.shutdown();
	}
}
