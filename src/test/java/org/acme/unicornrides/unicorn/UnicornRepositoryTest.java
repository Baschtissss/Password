package org.acme.unicornrides.unicorn;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnicornRepositoryTest {
    private static Unicorn[] ALL_UNICORNS = {
            new Unicorn(104, "Glitter Star Magic", 58, 162, Arrays.asList()),
            new Unicorn(101, "Magic Bubble Gum", 63, 123, Arrays.asList()),
            new Unicorn(102, "Mystic Rainbow Tail", 93, 111, Arrays.asList()),
            new Unicorn(103, "Starlight Twinkles", 73, 143, Arrays.asList())
    };

    @Inject
    UnicornRepository unicornRepository;

    @Test
    @Order(10)
    public void testFindAll() {
        Collection<Unicorn> unicorns = unicornRepository.findAll();

        Assertions.assertThat(unicorns.size()).isEqualTo(4);
        Assertions.assertThat(unicorns).containsExactly(ALL_UNICORNS);
    }

    @Test
    @Order(11)
    public void testFindUnicornWithLeastReservations() {
        Optional<Unicorn> unicorn = unicornRepository.findUnicornWithLeastReservations();
        Assertions.assertThat(unicorn).isPresent();
        Assertions.assertThat(unicorn.get().getId()).isEqualTo(101);
    }

    @Test
    @Order(12)
    public void testUpdateUnicorn() {
        Unicorn unicorn = unicornRepository.findById(101);
        unicorn.setName(unicorn.getName() + " - new");
        Unicorn savedUnicorn = unicornRepository.update(unicorn);

        Assertions.assertThat(savedUnicorn.getName()).isEqualTo("Magic Bubble Gum - new");
    }

    @Test
    @Order(13)
    public void testCreateUnicorn() {
        Unicorn unicorn = new Unicorn();
        unicorn.setName("new Unicorn");
        unicorn.setMaxSpeedKmpH(122);
        unicorn.setWeightKg(77);
        Unicorn savedUnicorn = unicornRepository.create(unicorn);

        Assertions.assertThat(savedUnicorn.getId()).isGreaterThan(0);

    }
}
