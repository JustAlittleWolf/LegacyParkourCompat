# Product integration receipt: classic Elytra look trig

## Immutable product and build identities

- Existing code ready ref: `refs/campaign-ready/c-elytra-look-float-trig`
- Existing code ready commit: `56585180f8984e3f3bb5c341232f902a7bd8e8b1`
- Compiled Git tree: `44a3822b7ff3233be3dc6e029ed06896a2a778d1`
- Code commit: `50709449d250645a009ae506b921ac8a3774be21`
- Code commit parent: `bd0868c14555da30ef68deb7dde455477ee4bf64`
- Current-main ancestry included by the ready commit: `30aaee28da28e399309589862dca4843a97b1dd8`
- Code paths: `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_10/FloatTableFallFlyingLook.java` and `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/travel/FallFlyingLookHook.java`.
- Candidate input: `644d804c671b09eb420dbc934067202da6cbaf77`; the product implementation adapts its formula and eligibility to the existing product hook and helper. Candidate-only history is not included in product ancestry.

Build command on the compiled tree:

```text
gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` on commit `56585180f8984e3f3bb5c341232f902a7bd8e8b1` and tree `44a3822b7ff3233be3dc6e029ed06896a2a778d1`. The init script disables every Gradle `Test` task and `-x test` excludes the test task. The final merged-tree invocation reported all 19 actionable tasks up to date; its client compile input is the same code tree built successfully before the documentation-only primary-main merges. No tests, runtime, game, TAS, Gym, server or Docker launch was performed. Runtime parity is unverified.

## Accepted source evidence

All source comparisons and independent verdicts below are byte-bound in primary evidence commit `e77ffaa5620c8308dc566121011f4745b443a479`, which is an ancestor of the code ready commit.

| Evidence slice | Comparison SHA-256 | Verdict SHA-256 | Accepted scope |
|---|---|---|---|
| `glide-look-exact-range` | `886c3d43e7eb92d9aec14d804669c122fef38f3520d7c073d5e899109921eb55` | `1f0cc3e38af95aea5a386c729d6eafd1744775ffe83ad1a97ca0114a243eea9a` | Direct-radian look expression first observed at 1.13; retained through inspected 1.14.4. Exact transition bound is after 1.12.2 and no later than 1.13. |
| `glide-trig-mid` | `50ed17704f422fe9f99c0f6f4de7758d622ecf572d7a9d727bd40dc2538220fb` | `5c7e5102aed63c8444cc2db765f135203c4ff15a7ceb6171191cf67955b7ec6d` | Float lookup continuity across the explicitly inspected 1.14.4–1.20.6 releases and listed Yarn snapshots. |
| `glide-trig-later` | `0165c239fd885fa32e254a09cfe07c06b27bdc3a25a7f00a380d8ee25d3ab44c` | `2669e3a9f3f2aa77ab997280054fa3d2eb75e4aa3dad49b2a7a5d18ef93da2a6` | The 1.21.10 float lookup is followed by the double-indexing path in 1.21.11; exact adjacent boundary verified. |

## Integrated operation and limits

For the admitted classic case—client `Player`, during the existing fall-flying movement path, with an `Items.ELYTRA` stack in the chest slot—the provider reconstructs the accepted float look expression through the existing `FallFlyingLookHook`, using `LegacyTrig`. The existing `LivingEntity` redirect feeds that same hook to the glide update; its lift-force sample also reads the same `getLookAngle` vector through the hook. Non-player entities and other chest items retain the supplied vanilla vector. There is no added zero-angle guard.

The provider is annotated `V1_21_10`. For selected 1.21.11, the resolver does not select this older provider, leaving the native double-index lookup intact. The existing 1.11.2 head-yaw and 1.12.2 providers remain in place. This receipt does not claim that the existing 1.12.2 provider fully models every look angle, resolve the separate `elytra-look-guard` glide correction, emulate activation/equipment production, or establish runtime parity.

## Intended central finding associations

- `development/research/backlog.json` coverage pair `1.12.2--1.13.2`: associate the accepted direct-radian expression transition with this look-vector operation only.
- `development/research/backlog.json` coverage pair `1.20.4--1.20.6`: associate the accepted mid-range float-lookup continuity evidence with the same operation.
- `development/research/backlog.json` coverage pair `1.21.10--1.21.11`: associate the accepted final float-to-double lookup boundary with this operation.
- Keep `development/research/findings/1.12.2--1.13.2/elytra-look-guard.md` partial and separately tracked; this batch does not close its glide travel guard. The adjacent `development/research/findings/1.11.2--1.12.2/fall-flying-look-yaw.md` describes the preserved earlier provider and is not revised by this batch.

This receipt supplies immutable integration and build associations only; source acceptance remains bound to the comparison and verdict identities above.
