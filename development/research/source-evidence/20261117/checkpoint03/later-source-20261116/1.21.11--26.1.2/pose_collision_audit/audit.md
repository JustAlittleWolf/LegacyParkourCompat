# Independent source audit: pose and collision dimensions

Verdict: ACCEPT (bounded NO_DIFF slice only)

## Binding

- Audited candidate: `build/later-source-20261116/1.21.11--26.1.2/pose_collision/coverage.md`
- Candidate SHA-256: `0D4F119C86E0F1C14A67E114F5013BE74889375C182B838FD914E2E49B9923FB`
- Exact pair: 1.21.11 Mojmap → 26.1.2 unobfuscated.
- This is an independent source-slice audit, not pair closure, runtime parity, implementation readiness, or evidence that endpoint world collision answers are equal.

## Source identity and integrity

I independently matched both endpoint version IDs, mappings, ready markers, provenance, source manifests, and artifact manifests to the canonical source trees before semantic review. The candidate's seven Java source hashes were then checked against canonical file bytes and the corresponding source manifest entries: 14/14 endpoint files matched with zero failures.

Endpoint identity digests:

- 1.21.11 ready marker `0AD98D0EBD654650492C97EB58BC324D33864787F0CE10D99FAEC3AE2F5B804B`; provenance `99C9FB741EB5E4AC8FB5780E2158DA324D7C8B6AFC179D494E5986F78FE29F72`; source manifest `0C4D83FEF84C101D9DB88F331ACEC7960F3C8346228C28995E6CF57BBFCAE555`; artifact manifest `C8210B15012DCC4E109C2F73015B4117ABEC1F8AB04FDD375DBC11419E9E246C`.
- 26.1.2 ready marker `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7`; provenance `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`; source manifest `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0`; artifact manifest `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92`.

The seven verified source pairs (old SHA → new SHA) are:

- `net/minecraft/client/player/LocalPlayer.java`: `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` → `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`
- `net/minecraft/world/entity/player/Player.java`: `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` → `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d`
- `net/minecraft/world/entity/LivingEntity.java`: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` → `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`
- `net/minecraft/world/entity/EntityType.java`: `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` → `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229`
- `net/minecraft/world/entity/EntityDimensions.java`: `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` → `27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a`
- `net/minecraft/world/entity/Entity.java`: `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` → `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- `net/minecraft/world/level/CollisionGetter.java`: `ca2a5c4561fa8b263d7ce8513a152f9c1d6537f4f011859351ce58b39a7f4e70` → `dddff4897e8d5d01e3ac132d85c474e6a3b9bae928e48b8c907aa9eabdb7fb01`

## Independent comparison

The bounded claim is supported. `LocalPlayer` retains the crouching predicate's ordered gates and the pose-fit checks. `Player.updatePlayerPose` retains the swimming fit precheck, desired-pose selection, fallback crouching fit, and swimming fallback. The desired-pose priorities and `canPlayerFitWithinBlocksAndEntitiesWhen` box-at-current-position with `1.0E-7` deflation are unchanged in the inspected bodies. `CollisionGetter` retains the same short-circuit evaluation order: block collision, entity collision, then world-border collision.

The pose setter/synced-data callback still reaches `refreshDimensions`. That method calculates dimensions for the new pose, assigns dimensions and eye height, and reapplies the current position to reconstruct the AABB. The size-growth adjustment retains its client/first-tick/no-physics/size/player gates and excludes `Player`; it therefore does not move this local player's position on this route. `Player` has no dimensions override; inherited `LivingEntity` dimensions continue through entity-type dimensions and the same age/scale path. The player type remains `(0.6F, 1.8F)` with eye height `1.62F`, and the relevant `EntityDimensions` bounding-box and scale formulas match.

I additionally checked the intermediate dynamic-super call in the client tick route, which the candidate's seven-file table does not separately list: `LocalPlayer` extends `AbstractClientPlayer`; its `tick` calls `clientAvatarState.tick(position, deltaMovement)` and then `super.tick()` at both endpoints. The exact `AbstractClientPlayer.java` hashes are 1.21.11 `AFE84AB1491A17AEECCF895CA4ECB17E941B8E517C980E31281B3D463FC593BD` and 26.1.2 `7825A8D4E8E24928CBA18F68109C91479B78FB14FCD8B81F0E045B8D02A559CC`. This fills the route context without widening the bounded NO_DIFF claim; the route proceeds to the compared `Player.tick` pose updater in the same order.

The report correctly limits its conclusion: equivalent query construction and algorithm do not establish equal collision results from release-specific world data or shape providers. It also expressly leaves other movement routes, intermediate releases, pair-wide coverage, and runtime behavior open.

## Audit conclusion

ACCEPT the immutable candidate revision as an accurate bounded NO_DIFF source report for the stated pose-selection / fit-query / player-dimension-refresh slice. No unsupported world-answer claim was found. This verdict does not close the pair or attest to any unexamined dependency or runtime result.
