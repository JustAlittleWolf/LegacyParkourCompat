# Erratum 5 — non-primary caller dispositions

This addendum completes the census dispositions for the remaining non-primary `Shapes.create(AABB)` callsites. The independent bounded report `../shapes-create-excluded-callsites/report.md` has source and publication hash ledgers alongside it.

- EndPortalBlock's AABB intersection is gated by `level instanceof ServerLevel` and controls server-side dimension transfer, so this shape conversion does not run on LocalPlayer's logical-client movement path.
- HopperBlockEntity gates the AABB/suck-shape intersection to `ItemEntity`; the player cannot satisfy it.
- CollisionGetter/EntityGetter's `isUnobstructed(Entity)` AABB conversion has no LocalPlayer tick, collision, or position-response caller in the exact three trees. The found uses are mob/entity spawn or placement predicates.
- Boat.getGroundFriction is vehicle physics, outside scope; ServerGamePacketListenerImpl is server validation, not client historical movement. For eligible-profile connections, conflicting server validation remains governed by the project's server policy.
- 1.17+ Entity.refreshDimensions shape allocation is logical-server and non-Player only; JigsawPlacement is world-generation geometry; LevelRenderer and GameEventListenerRenderer are render/debug geometry.

After this exclusion audit, the only still-open LocalPlayer-reachable `Shapes.create(AABB)` callsite from this census is the dynamic `ShulkerBoxBlock.getShape` provider, pending independent progress-domain collision disposition. The displacement-expanded `CollisionSpliterator.<init>` block query is instead a known, independently accepted conditional 1.16.5→1.17 collision-axis difference, not an open no-difference candidate. Current-box border queries, entity-provider AABBs, and the block-ack rollback query have the bounded dispositions recorded in Errata 4, 2, and 1 respectively.

This is an API-use census and bounded caller audit; complete changed-path/member/tick coverage and the independent full-pair verdict remain separate.
