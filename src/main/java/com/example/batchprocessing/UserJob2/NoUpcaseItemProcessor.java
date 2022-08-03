package com.example.batchprocessing.UserJob2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.batchprocessing.Person;

public class NoUpcaseItemProcessor implements ItemProcessor<Person, Person> {

	private static final Logger log = LoggerFactory.getLogger(NoUpcaseItemProcessor.class);

	@Override
	public Person process(final Person person) throws Exception {
		log.info("Converting (" + person + ") into (" + person + ")");

		return person;
	}

}
