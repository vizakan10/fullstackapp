package org.example.try5sevi.repo;

import cli.TicketConfig;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * The {@code TicketRepository} interface provides CRUD operations for {@code TicketConfig} objects.
 * It extends the {@code MongoRepository} interface provided by Spring Data MongoDB.
 */
@Repository
public interface TicketRepository extends MongoRepository<TicketConfig, String> {
}
