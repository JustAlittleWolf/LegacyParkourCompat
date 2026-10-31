# Bounded route audit: `LocalPlayer.updateAutoJump` — 1.15.2 → 1.16.5 Mojmap

## Verdict

**LocalPlayer body and its direct candidate-filter migration: CLOSED EQUIVALENT.** The remaining exact movement consumer is `LocalPlayer.updateAutoJump` → the ordinary `CollisionGetter.getCollisions` block stream. The stream's already accepted full-cube fast-path delta remains a *conditional dependency candidate* for auto-jump, but its effect on this caller is not established by the existing Entity.move witness. Keep this one caller-specific applicability question open; do not reopen the whole LocalPlayer class, entity-filter migration, or collision collector inventory.

This is not an independently accepted auto-jump finding. It does not assert an auto-jump trajectory, setup frequency, or runtime behavior.

## Exact caller and method

In 1.15.2, `LocalPlayer.move(MoverType, Vec3)` calls `super.move`, then `updateAutoJump((float)(getX()-oldX), (float)(getZ()-oldZ))` (`LocalPlayer.java:851–856`); the same order and float casts occur in 1.16.5 (`:863–868`). The protected method is at old `LocalPlayer.java:862–960`, new `:874–972`; `canAutoJump` is old `:962–970`, new `:974–982`. The gates and candidate geometry operations match: auto-jump enabled; cooldown nonpositive; grounded; not staying on ground surface; not passenger; moving input; block jump factor ≥ 1; measured displacement with input/yaw fallback when squared length ≤ `0.001F`; normalized direction; forward-dot cutoff `< -0.15F`; head clearance; jump-effect height; broadphase AABB; two lateral line-segment checks; vertical clearance and `(0.5, q]` height window; then `autoJumpTime = 1`.

The only call-site expression change in this body is `level.getCollisions(this, aabb, Collections.emptySet())` (old `:914–917`) to `level.getCollisions(this, aabb, entity -> true)` (new `:926–929`). There is no changed operation order, numeric constant, float conversion, obstacle decision, or cooldown write in this member.

## Direct API migration

Old `CollisionGetter.getCollisions(entity, aabb, Set<Entity>)` concatenates block collisions with entity collisions filtered by the exclusion set (`CollisionGetter.java:63–65`). New `getCollisions(entity, aabb, Predicate<Entity>)` concatenates the same block collision stream with entity collisions filtered by the predicate (`:54–56`). For this LocalPlayer caller, the old empty exclusion set and new always-true predicate admit the same entities. The separately reviewed `EntityGetter` matching route preserves the LocalPlayer candidate shapes, including Boat and live Shulker handling. This migration is closed for this caller; it does not claim equivalence for other source-entity subclasses or modded entity overrides.

## Remaining dependency and precise limit

The caller consumes collision AABBs from the default block-collision overload. The existing collision-collection report (SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`) establishes a narrow raw-AABB versus `Shapes.create(AABB)` full-cube fast-path difference, with a concrete reachable **Entity.move** multi-axis witness. That report explicitly does not establish an `updateAutoJump` witness.

For auto-jump, a collected block affects the response only after its AABB intersects one of the two side segments `vec310→vec311` or `vec312→vec313`. The method uses a broader horizontal query AABB (`inflate(width, 0, width)`) to enumerate candidates, then tests those inner segments. I did not establish whether any grid-snapped-only full-cube candidate can both occur in the changed collector fringe and intersect either tested segment under this method's actual endpoint, direction, width, pose and `canAutoJump` gates. Nor did I prove that such an intersection is impossible for every accepted `Shapes.create` grid resolution. Thus the Entity.move witness cannot be transferred to auto-jump, and the member should remain a single named conditional caller question until that geometry is resolved.

No other changed dependency was found in this bounded route. Existing collision entity-filter, shape-context, border, and movement-body findings retain their separate scopes.

## Evidence identity

The relevant exact-file SHA-256 values, matching the existing LocalPlayer member inventory and canonical source manifests, are:

- 1.15.2 `LocalPlayer.java`: `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`
- 1.16.5 `LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
- 1.15.2 `CollisionGetter.java`: `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`
- 1.16.5 `CollisionGetter.java`: `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`
- 1.16.5 `CollisionSpliterator.java`: `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`
- 1.15.2 `Entity.java`: `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`
- 1.16.5 `Entity.java`: `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`

No product, wiki, backlog, canonical-source, build, test, decompilation, game or runtime files were changed or used.
