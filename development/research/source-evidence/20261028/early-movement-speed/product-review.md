# Final product review: 1.8 movement-speed adapter

## Scope and input

Reviewed product tip `e108667d5ccc06698971ea75f6441acf82fee48c` on `fix/1818-speed-product-20261028`, parent `8139aa93c366cc584d5a3257848169779ccf9327`. The tip is the branch HEAD. Its diff from the parent changes only `UncappedPlayerMovementSpeed.java` and `MovementSpeedModifierIdentity.java`. I incorporated the accepted Java 8 order supplement (`.native-source/20261028/1818-speed-java8-order/verification.md`, SHA-256 `f9427e6ca95f4e128af1ba299d881a74289e665ac1d64520cddf4dbe65ab5005`) and independent audit (`.native-source/20261028/1818-speed-java8-order-audit/audit.md`, SHA-256 `690e2d724c97f81016d9767c10e46cbcf625182777dc446fe62d4442db2bfe1e`), plus the prior accepted boundary and modifier-collection evidence.

**Verdict: ACCEPT within the documented supported scope.** The adapter now reconstructs the source-supported Speed/Slowness/sprint operation-2 subsets using the audited OpenJDK 8u412 reference buckets. It conservatively retains native behavior whenever the history or current modifier set exceeds that bounded scope.

## Review checks

- **Known operation-2 subsets:** the identity helper maps the three exact modern identifiers to the source-bound 1.8 UUIDs. `openJdk8u412HashSetBucket` applies UUID hash folding, OpenJDK 8 spread, and a 16-slot mask. The audited slots are Slowness 5, sprint 6, and Speed 13. The adapter sorts each category ascending by bucket and rejects collisions. Thus each known-only subset has the accepted reference order, independent of insertion/removal order.
- **Eligibility and fallback:** the adapter requires complete, total-only history; no more than three current modifiers; known identities in both observed history and current modifiers; operation 2 for every current modifier; and collision-free buckets. Incomplete/unknown histories, other operations, excess modifiers, or bucket collisions return empty for the mixin to preserve native behavior. These gates cover the stated known-only history scope without treating arbitrary server/equipment modifier IDs as historical UUIDs.
- **Calculation and cutoff:** the provider retains historical operation sequencing (0, 1, 2), comparison-based `[0, Double.MAX_VALUE]` clamping, and double-to-float conversion. For the supported case, only operation 2 is present and it now uses reconstructed reference order. `V1_8` covers 1.8.0–1.8.4; `V1_8_5` begins at the 1.8.5 cap boundary. The resolver selects changes at or later than the chosen profile, and the existing later native provider returns empty, so this adapter does not cross the cutoff.
- **Previously open standard-stack case:** Speed+sprinting and the other known built-in operation-2 subsets now enter the historical calculation instead of falling back. The uncapped result can therefore exceed the modern 1024 cap for reachable high base/modifier states.
- **No unintended product changes:** the tip diff is limited to the adapter and identity helper. The runtime snapshot path, client `getSpeed` injection/native fallback, hook contract, V1_20_5 implementation, and profile/resolver code are unchanged from the reviewed parent. The adapter consumes the raw base/modifier snapshot and never uses capped `getValue()`.

## Bound

The Java ordering derivation is accepted for the named OpenJDK 8u412-b08 reference source and these three known UUIDs. The launcher metadata specifies Java 8 major version but does not pin vendor/update; the evidence and implementation comment do not claim a Java Collections ordering guarantee for every Java 8 runtime. Any additional identity or non-total history takes the native fallback. This review accepts the adapter within that explicit bound; it does not establish runtime parity.

No source edits, builds, tests, Java/game launches, or runtime execution were performed.