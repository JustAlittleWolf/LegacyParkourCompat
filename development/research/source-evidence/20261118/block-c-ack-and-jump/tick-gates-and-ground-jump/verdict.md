# Independent audit: 1.21.1 → 1.21.3 tick gates

**Terminal: COMPLETED.** The ground-jump claim is independently accepted for the exact conditional movement difference, with an ordinary vanilla reachable setup. The bounded tick/sprint/jump/flight gate comparison was checked as grouped coverage. This does not freeze full-pair coverage.

## Immutable inputs and source identities

- Queue report SHA-256: `0c8dddbbe9777945540d313814c3c0a8ccb121ab21ca37139f1bfc180d006ac9` (verified).
- Ground-jump finding SHA-256: `8ff8bf6711c07ca0fdc45866b5f5d7b909f66510a4cc48695153439490ad6d04` (verified).
- Scope: canonical `decompiled_minecraft/{1.21.1,1.21.2,1.21.3}/mojmap/` only; no product, wiki or Git evidence used.
- `LivingEntity.java` SHA-256: 1.21.1 `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8`; 1.21.2 `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52`; 1.21.3 same bytes/hash as 1.21.2.
- 1.21.1 and 1.21.3 have adjacent Mojmap provenance, ready markers and source manifests. 1.21.2's nested `.decompile-provenance.json` exists (SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`). Thus the report's statement that provenance was absent is stale: it searched for a differently named adjacent file and missed 1.21.2's nested manifest. I did not validate all artifact provenance chains.

## Ground-jump claim

Compared `LivingEntity.jumpFromGround()` and its `aiStep()` admission call in all three releases. In 1.21.1 the method reads the current vector and writes `($$1.x, $$0, $$1.z)` after the unchanged `jumpPower > 1.0E-5F` check. In both 1.21.2 and 1.21.3 it writes `($$1.x, Math.max($$0, $$1.y), $$1.z)`. The ordinary admission at `aiStep` requires jump input, a grounded/low-water condition and `noJumpDelay == 0`; the direct grounded branch invokes the method without clamping or testing current Y. This confirms the operation and shows it was present by 1.21.2.

A concrete ordinary vanilla path to the precondition is a player landing on a slime block: landing establishes grounded state while the slime bounce supplies positive Y velocity; a jump input on the following player tick can meet the ordinary grounded gate with Y above jump power. Then 1.21.3's max preserves that stronger upward component, while 1.21.1 replaces it. The gate and consumer make this a locally reachable player movement behavior, not merely an arbitrary method argument.

## Grouped coverage verdict

The source sections reported for LocalPlayer pre-travel ordering/input snapshots, ordinary jump timers/admission, sprint predicates, ability flight admission, and local post-travel flight shutdown were spot-retraced in all three exact versions where source exists. No further tick/sprint/jump/flight difference was found in those bounded slices. The `LivingEntity.aiStep()` ordinary jump admission retains the same relevant grounded/no-delay structure in 1.21.1, .2 and .3. The 1.21.2 jump method matches .3; no earlier introduction boundary than “after 1.21.1, at or before 1.21.2” is needed for the audited claim.

## Open bounds

- Actor/state bound: the accepted jump case requires a grounded player with jump admitted and current `Y > getJumpPower()`. The slime landing/bounce sequence supplies a normal vanilla route. This finding does not claim every grounded jump is affected.
- No exhaustive closure of all movement tick consumers or block callback consumers; the separately excluded block-effects claim was not reviewed or used for acceptance.
- No full-pair freeze; unrelated movement operation inventory and all dependencies remain outside this bounded audit.
- No code changes, tests, build, game launch, source generation or backlog access/write.