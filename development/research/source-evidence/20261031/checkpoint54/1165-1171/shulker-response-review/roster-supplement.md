# Roster supplement: query membership and client player registration

After freezing the initial source roster and while tracing the entity-query dispatch, I added the concrete backing-storage members required to verify the LocalPlayer inclusion predicate: 1.16.5 `LevelChunk.getEntities` and the 1.17+ `TransientEntitySectionManager` / `EntitySectionStorage` path. These methods implement entity registration and bounding-box intersection for the already listed client-level query. This supplements, and does not replace, the original frozen roster.
The 1.17+ `EntitySection` predicate/consumer loop was also checked to confirm that intersecting entity boxes are delivered to the level query.
