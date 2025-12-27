package kz.trendprice.server.storeservice.specification;

import kz.trendprice.server.storeservice.entity.StoreBranche;
import org.springframework.data.jpa.domain.Specification;

public class StoreBranchSpecification {
    public static Specification<StoreBranche> hasStoreTitle(String title) {
        return ((root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("store").get("title")), "%" + title.toLowerCase() + "%"
                )
        );
    }

    public static Specification<StoreBranche> hasStatus(String statusTitle) {
        return ((root, query, criteriaBuilder) ->
                statusTitle == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.join("status").get("title")), "%" + statusTitle.toLowerCase() + "%"
                )
        );
    }

    public static Specification<StoreBranche> hasOpenHours(String openHours) {
        return ((root, query, criteriaBuilder) ->
                openHours == null ? null : criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("openHours")), "%" + openHours.toLowerCase() + "%"
                )
        );
    }
}
