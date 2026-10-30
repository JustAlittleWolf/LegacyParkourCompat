# Independent source audit: Honey and Web callback reuse

## Verdict

**Accept the bounded callback-body equivalence finding** for Honey and Web in 1.21.2/1.21.3 versus 26.2, including the stated synchronous historical dispatch order. The closure's narrow conclusion is supported. This does not establish other callback families, candidate eligibility/traversal, an intermediate version boundary, or runtime parity.

## Inputs and source identity

- Reviewed `.native-source/20261030/ordinary-pool/typed-callbacks/closure.md`; SHA-256 `b961cd61ffcd45871839b0978b1dc2bc804226a5347c03bd0f9236f08d90665c` (matches assigned identity).
- Scheduler context `.native-source/20261030/dda-collector-flush/report.md`; SHA-256 `bb3d871fa1d65051faa3a9a36b1c3839f07406bf18384c60aeb52f8d274d261e`.
- Independent scheduler audit `.native-source/20261030/dda-collector-flush-audit/audit.md`; SHA-256 `d48a3bca758d408303cd5acbe5291a72ca5bf250c6faf3c4f02532b00724e1ba`.
- Independently hashed canonical callback/helper files under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` match all hashes listed in the closure. The four 1.21.2 files and their 1.21.3 counterparts are byte-identical: HoneyBlock `40bf4e5d55f44f7aec9716a46b11cd6ad08ed861be4d63a85544a1bfb595a71d`; WebBlock `ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414`; Entity `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; BlockBehaviour `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133`. The 26.2 files also match their listed identities: HoneyBlock `ad1626f8b2c7ac39bdae01de7f8d5dbec0fb4193057dea33e39e0c7cc3c1b6aa`; WebBlock `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5`; Entity `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; BlockBehaviour `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`.
- The additional `Level.java` helper check used for Honey's random-source accessor has SHA-256 `ff4d57a3df0b850ba02dc798c6aa6734174790b9fd17673261f33f03d44821e1` (1.21.2) and `1ae4f565ad909f4e90786089b5b4f125e8a7d18e2ef7d8c376172a5787fc5639` (26.2).

## Findings

- **Historical dispatch order is confirmed.** Old `Entity.checkInsideBlocks` marks the non-air position before its shape gate, then invokes `state.entityInside(...)`, `onInsideBlock(state)`, and only after both return adds the state to the summary (`1.21.2/mojmap/.../Entity.java:1052–1071`). Its catch wraps failures in `ReportedException`. This agrees with the supplied scheduler context and its audit. The callback conclusion is conditional on preserving that caller order and its separately sourced candidate/shape rules.
- **Honey callback equivalence is supported.** Old and modern bodies retain the same sliding predicate order and constants, formulas, achievement check, movement write, fall-distance reset, slide-effect gates, and final superclass call. The old `level.random` and modern `level.getRandom()` refer to the same `Level.random` field at both endpoints (`Level.getRandom()` returns `this.random`). The added modern `effectApplier` and `isPrecise` arguments are only forwarded to `BlockBehaviour.entityInside`, whose implementation is empty in 26.2; the old base callback is empty too. Direct entity/world responses therefore remain synchronous and ordered as stated.
- **Web callback and helper equivalence is supported.** Both callbacks select the same two multipliers using the same `LivingEntity`/`WEAVING` gate, then immediately call `entity.makeStuckInBlock`. Old and modern helper bodies both reset fall distance before assigning `stuckSpeedMultiplier`; the numeric zero literal follows the respective old float and modern double field representation. No effect-applier behavior is registered or used.

## Boundary

The closure correctly leaves eligibility, traversal, other callback bodies, release-introduction boundaries, and trajectory parity open. Its minimal reuse order is justified only when the legacy synchronous dispatch and callback-specific eligibility are preserved. No correction is required within this bounded finding.
