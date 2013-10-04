package javadrivertest;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        int loglines = 0;

        Cluster cluster = Cluster.builder().addContactPoint("localhost").build();
        Session session = cluster.connect("testks");

        long epoch = System.currentTimeMillis();
        for (int sequence = 1; sequence <= 1000; sequence++) {
            session.execute(QueryBuilder
                                .insertInto("table1")
                                .values(new String[]{"epoch", "sequence", "bogus"},
                                        new Object[]{epoch, sequence, "bogusvalue"})
                                .setConsistencyLevel(ConsistencyLevel.QUORUM));
        }
        System.out.println(String.format("%d: Rows written", loglines++));

        while (true) {
            int exceptionCounter = 0;
            for (int sequence = 1; sequence <= 1000; sequence++) {
                try {
                    session.execute(QueryBuilder
                                   .select().all().from("table1")
                            .where(QueryBuilder.eq("epoch", epoch)).and(QueryBuilder.eq("sequence", sequence))
                            .setConsistencyLevel(ConsistencyLevel.QUORUM));
                } catch (Exception e) {
                    exceptionCounter++;
                    System.out.println(e.getMessage());
                }
            }

            System.out.printf("%d: Tried to fetch 1000 rows and got %d exceptions%n", loglines++, exceptionCounter);
        }


    }
}
