package uk.co.colleagueassit.dataloader;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;

public class DataLoaderFtpChannel {

	private static final Logger LOGGER = Logger.getLogger(DataLoaderFtpChannel.class);

	@Test
	public void runDemo() throws Exception {
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(
				"META-INF/spring/integration/ftp-context.xml");

		PollableChannel ftpChannel = ctx.getBean("ftpChannel", PollableChannel.class);

		Message<?> message1 = ftpChannel.receive(2000);
		Message<?> message2 = ftpChannel.receive(2000);
		Message<?> message3 = ftpChannel.receive(1000);

		LOGGER.info(String.format("Received first file message: %s.", message1));
		LOGGER.info(String.format("Received second file message: %s.", message2));
		LOGGER.info(String.format("Received nothing else: %s.", message3));

		assertNotNull(message1);
		assertNotNull(message2);
		assertNull("Was NOT expecting a third message.", message3);

		ctx.close();
	}

}
