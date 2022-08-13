package com.example.batchprocessing.config.chunk.testChunkJobDBtoFile;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.batchprocessing.JobCompletionNotificationListener;
import com.example.batchprocessing.dto.test.Person;

// tag::setup[]
@Configuration
public class DBtoFileBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	private Resource outputResource = new FileSystemResource("person.txt");
	// end::setup[]

	// tag::readerwriterprocessor[]
    @Bean    
    public JdbcCursorItemReader<Person> jdbcCursorItemReader(DataSource dataSource) {
    	return new JdbcCursorItemReaderBuilder<Person>()
    			.name("jdbcCursorItemReader")
    			.fetchSize(100)
    			.dataSource(dataSource)
    			.rowMapper(new BeanPropertyRowMapper<Person>(Person.class))
    			.sql("SELECT first_name, last_name FROM people")
    			.build();
    	}

	@Bean
	public NoUpcaseItemProcessor processorForDBtoFile() {
		return new NoUpcaseItemProcessor();
	}

    @Bean    
    public FlatFileItemWriter<Person> flatFileItemWriter() {
    	FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();
    	writer.setResource(outputResource);
    	writer.setAppendAllowed(true);
    	writer.setLineAggregator(new DelimitedLineAggregator<Person>() {
    		{
    			setDelimiter(",");
    			setFieldExtractor(new BeanWrapperFieldExtractor<Person>() {
    				{
    					setNames(new String[] { "firstName", "lastName" });
    					}
    				});
    			}        
    		});
    	return writer;    
    }


	// end::readerwriterprocessor[]
	@Bean
	public Job chunkDBtoFileJob(JobCompletionNotificationListener listener, Step chunkDBtoFileStep) {
		return jobBuilderFactory.get("chunkDBtoFileJob")
			.incrementer(new RunIdIncrementer())
			.listener(listener)
			.flow(chunkDBtoFileStep)
			.end()
			.build();
	}

	@Bean
	public Step chunkDBtoFileStep(JdbcCursorItemReader<Person> jdbcCursorItemReader,FlatFileItemWriter<Person> flatFileItemWriter) {
		return stepBuilderFactory.get("chunkDBtoFileStep")
			.<Person, Person> chunk(10)
			.reader(jdbcCursorItemReader)
			.processor(processorForDBtoFile())
			.writer(flatFileItemWriter)
			.build();
	}
	// end::jobstep[]
}
