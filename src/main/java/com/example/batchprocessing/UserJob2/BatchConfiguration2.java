package com.example.batchprocessing.UserJob2;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.batchprocessing.JobCompletionNotificationListener;
import com.example.batchprocessing.Person;

// tag::setup[]
@Configuration
@EnableBatchProcessing
public class BatchConfiguration2 {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	// end::setup[]

	// tag::readerwriterprocessor[]
	@Bean
	public FlatFileItemReader<Person> reader2() {
		return new FlatFileItemReaderBuilder<Person>()
			.name("personItemReader")
			.resource(new ClassPathResource("sample-data.csv"))
			.delimited()
			.names(new String[]{"firstName", "lastName"})
			.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
				setTargetType(Person.class);
			}})
			.build();
	}

	@Bean
	public NoUpcaseItemProcessor processor2() {
		return new NoUpcaseItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Person> writer2(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Person>()
			.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
			.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
			.dataSource(dataSource)
			.build();
	}
	// end::readerwriterprocessor[]
	@Bean
	public Job importUserJob2(JobCompletionNotificationListener listener, Step step2) {
		return jobBuilderFactory.get("importUserJob2")
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			.flow(step2)
			.end()
			.build();
	}

	@Bean
	public Step step2(JdbcBatchItemWriter<Person> writer2) {
		return stepBuilderFactory.get("step2")
			.<Person, Person> chunk(10)
			.reader(reader2())
			.processor(processor2())
			.writer(writer2)
			.build();
	}
	// end::jobstep[]
}
