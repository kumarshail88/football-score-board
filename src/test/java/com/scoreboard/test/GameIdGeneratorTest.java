package com.scoreboard.test;

import com.scoreboard.utils.GameIdGenerator;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

public class GameIdGeneratorTest {

    @Test
    public void testIdGenerationConcurrently() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(50);
        GameIdGenerator gameIdGenerator = new GameIdGenerator();
        CountDownLatch latch = new CountDownLatch(50);
        for (int i = 1; i <= 50; i++){
            service.submit(() -> {
                gameIdGenerator.getNextId();
                latch.countDown();
            });
        }
        latch.await();
        assertEquals(Long.valueOf(51), gameIdGenerator.getNextId());
    }

}
