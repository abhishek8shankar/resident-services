package io.mosip.resident.repository;

import io.mosip.resident.entity.ResidentTransactionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Interface ResidentTransactionRepository.
 * @author Kamesh Shekhar Prasad.
 * @since 1.2.0.1
 */
@Repository
public interface ResidentTransactionRepository extends JpaRepository<ResidentTransactionEntity, String> {
    List<ResidentTransactionEntity> findByRequestTrnIdAndRefIdOrderByCrDtimesDesc(String requestTrnId, String refId);
    ResidentTransactionEntity findByAid(String aid);

    List<ResidentTransactionEntity> findByTokenId(String token);


    @Query(value = "Select new ResidentTransactionEntity( eventId, statusComment , crDtimes, statusCode, updDtimes, requestTypeCode) " +
            "from ResidentTransactionEntity where tokenId=:tokenId AND crDtimes>= :fromDateTime AND crDtimes<= :toDateTime  " +
            " AND authTypeCode in :residentTransactionType " +
            " AND (eventId like %:searchText%" +
            " OR statusComment like %:searchText% " +
            " OR statusCode like %:searchText%) " +
            "ORDER BY pinnedStatus DESC" )
    List<ResidentTransactionEntity> findByTokenAndTransactionType(@Param("tokenId") String tokenId,
                                                                  @Param("fromDateTime") LocalDateTime fromDateTime,
                                                                  @Param("toDateTime") LocalDateTime toDateTime,
                                                                  @Param("residentTransactionType") List<String> residentTransactionType,
                                                                  Pageable pagaeable, @Param("searchText") String searchText);

    @Query(value = "Select new ResidentTransactionEntity( eventId, statusComment , crDtimes, statusCode, updDtimes, requestTypeCode) " +
            "from ResidentTransactionEntity where tokenId=:tokenId " +
            " AND authTypeCode in :residentTransactionType " +
            " AND (eventId like %:searchText%" +
            " OR statusComment like %:searchText% " +
            " OR statusCode like %:searchText%) " +
            " ORDER BY pinnedStatus DESC" )
    List<ResidentTransactionEntity> findByTokenWithoutDate(@Param("tokenId") String tokenId,
                                                           @Param("residentTransactionType") List<String> residentTransactionType,
                                                           Pageable pagaeable, @Param("searchText") String searchText);

    @Query(value = "Select new ResidentTransactionEntity(aid) " +
            "from ResidentTransactionEntity where tokenId=:tokenId "  +
            " AND authTypeCode =:residentTransactionType ORDER BY crDtimes DESC" )
    List<ResidentTransactionEntity> findRequestIdByToken(@Param("tokenId") String tokenId,@Param("residentTransactionType")
            String residentTransactionType, Pageable pagaeable);

    @Query(value = "Select new ResidentTransactionEntity( eventId, statusComment , crDtimes, statusCode, updDtimes, requestTypeCode) " +
            "from ResidentTransactionEntity where tokenId=:tokenId AND crDtimes>= :fromDateTime AND crDtimes<= :toDateTime  " +
            " AND (eventId like %:searchText%" +
            " OR statusComment like %:searchText% " +
            " OR statusCode like %:searchText%) " +
            "ORDER BY pinnedStatus DESC" )
    List<ResidentTransactionEntity> findByTokenWithoutServiceType( @Param("tokenId") String tokenId,
                                                                   @Param("fromDateTime") LocalDateTime fromDateTime,
                                                                   @Param("toDateTime") LocalDateTime toDateTime,
                                                                   Pageable pagaeable, @Param("searchText") String searchText);

    @Query(value = "Select new ResidentTransactionEntity( eventId, statusComment , crDtimes, statusCode, updDtimes, requestTypeCode) " +
            "from ResidentTransactionEntity where tokenId=:tokenId " +
            " AND (eventId like %:searchText%" +
            " OR statusComment like %:searchText% " +
            " OR statusCode like %:searchText%) " +
            " ORDER BY pinnedStatus DESC" )
    List<ResidentTransactionEntity> findByTokenWithoutServiceTypeAndDate(@Param("tokenId") String tokenId,
                                                                         Pageable pagaeable, @Param("searchText") String searchText);
}