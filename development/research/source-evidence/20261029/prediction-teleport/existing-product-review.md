# Product wiring review: prediction reconciliation after teleport

## Verdict: ACCEPT

Reviewed product base `9df63e8b6a59506ff91649eb3042a44ec2d3088c` against the accepted finding and the four assigned source-evidence reports. The product wiring matches the accepted contract: profiles at `V1_21_11` and later resolve a hook that keeps the saved prediction-time player position eligible despite the native teleport-sequence comparison; `V1_21_10` and earlier resolve a hook that leaves the native comparison in effect.

## Wiring review

- **Authoritative boundaries:** `NativeBlockPredictionRollback` is literally annotated `@MovementChange(emulates = ParkourVersion.V1_21_10)` and returns `false`. `BlockPredictionRollback` is literally annotated `@MovementChange(emulates = ParkourVersion.V1_21_11)` and returns `true`. Both are public concrete classes under their version packages and declare no constructor, so each has the public no-argument constructor required by discovery.
- **Resolution:** `ChangeResolver` groups by mechanic key, admits changes whose `emulates` is newer than or equal to the selected profile, and chooses the minimum enum ordinal. The enum orders `V1_21_10`, `V1_21_11`, `V26_1`, then `CURRENT`. Therefore `V1_21_10` and earlier choose the false change; `V1_21_11` through `V26_1` choose the true change. `CURRENT` resolves no historical changes and remains native.
- **Hook granularity and registration:** `BlockPredictionRollbackHook` represents one operation: whether reconciliation may pass its saved player position. It is the only hook implemented by either change. Client-only `MovementChangeDiscovery` scans version subpackages, reads the authoritative annotation, and independently registers every implemented annotated hook. No manual registration or exact-profile condition is needed.
- **Side and player gates:** The mixin is in the client source set and in the client mixin config. It queries `Minecraft.getInstance().player`, which is the local player; a null player also falls through to the native expression. `MovementRuntime.find` calls `appliesTo`, which rejects non-players and entities outside a client-side level before checking whether historical movement is enabled. This keeps logical-server/integrated-server execution out and preserves the vanilla path when the profile is current.
- **Mixin target and operation order:** The mixin targets `BlockStatePredictionHandler` and applies `@ModifyExpressionValue` to the exact `endPredictionsUpTo(ILnet/minecraft/client/multiplayer/ClientLevel;)V` method. Its expression is the native `this.lastTeleportSequence < sequence` test, and the modification returns `vanilla || hookValue`. Thus the false hook preserves the native strict comparison, while the true hook makes the saved position eligible at and below the teleport boundary. The injection changes only that argument-eligibility predicate at the point where the method forms the saved-position argument.
- **Unchanged surrounding behavior and fallback:** No product patch replaces the reconciliation loop or `ClientLevel.syncBlockState`. Entry eligibility/removal, authoritative block-state synchronization, same-current-level gating and collision gating remain vanilla. A false/absent hook leaves the original comparison result untouched; outside the client/player/enabled gates the mixin also leaves the native result untouched.

## Evidence limit

The assigned source evidence verifies the comparison and matching method signatures in 1.21.11 and 26.1.2, and the alias closure verifies identical relevant methods across 26.1, 26.1.1 and 26.1.2. It establishes 26.1 as the first confirmed release in that sample. The product change comment additionally says 26.2 retains the comparison; that later-release assertion was not independently established from the assigned evidence. This does not change the wiring verdict for the accepted boundary, but the review makes no 26.2 source-compatibility or runtime claim.

## Exact input hashes

Product base commit: `9df63e8b6a59506ff91649eb3042a44ec2d3088c`.

Product file SHA-256 values are computed from each file's blob at that commit:

| Product file | SHA-256 |
|---|---|
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_10/NativeBlockPredictionRollback.java` | `229fce134889e0bc0acc4f6de9a5100ac6a45631e3fef8699492880f965a8b36` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_11/BlockPredictionRollback.java` | `00b07a27af46f857c8d5d44f29248c53741e8fb14543650999fd50d80fe72be6` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/prediction/BlockPredictionRollbackHook.java` | `5dc78362bea3edd60b9b15b6b2840ef3c261829191a77db7da397ccefb7bc098` |
| `src/client/java/me/wolfii/legacyparkourcompat/mixin/prediction/BlockStatePredictionHandlerMixin.java` | `d93fd9d41e3a59e7c25aecef4970f71460db8b5ac4d11cc2a2ac9d1a09f84805` |
| `src/client/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java` | `9b283d78ada525d2ee49ead63767fb852ae8f3a489d7588c5e77e02198aee37d` |
| `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` | `57f3b806f65b48ad0abb2612cdc67bf84c94d70c3a4a8ea230fd7d6f962ab763` |
| `src/client/java/me/wolfii/legacyparkourcompat/change/MovementChangeDiscovery.java` | `985729cbfef21344018328f115d38676d841b154470ad5009d6edf9a68975a70` |
| `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java` | `372b4572fb232d3251421f09cce59711470a419636df9da9d1d0eb9da2f9ae44` |
| `src/main/java/me/wolfii/legacyparkourcompat/impl/MovementControllerImpl.java` | `0409675535daa60f660441d2167833a44c027f2e276f2e45ce9567c98ca57b4e` |
| `src/client/resources/legacyparkourcompat.client.mixins.json` | `0cae2e628ac8ae93b42d49e3fd0cceadc5f1596296024d6a7eb3c2b172de4678` |
| `src/client/java/me/wolfii/legacyparkourcompat/LegacyParkourCompatClient.java` | `7c0c67ec8a4d5f1fa08ba36f37858403997a93ce34c377bae803aec2df9b1114` |

Assigned research input SHA-256 values:

| Evidence file | SHA-256 |
|---|---|
| `development/research/findings/1.21.11--26.1.2/prediction-reconciliation-after-teleport.md` | `eb95fae530e1050d09ad31b82572c2e3b92b5ab48ff2b5d1f954901f3cf72a54` |
| `development/research/source-evidence/20261029/prediction-teleport/source.md` | `c7684cd29c2e70044b54250a7f5fbef40fd196f2e3e85e9ae95c3c016b43924c` |
| `development/research/source-evidence/20261029/prediction-teleport/source-audit.md` | `8a34bc3585bbf46ebe72bfd3a742f05cbdba7fa533bebff391c790d363b7e811` |
| `development/research/source-evidence/20261029/prediction-teleport/26-1-aliases.md` | `632d4e2084c36039a471cb305b7f163afc1f9bd08bccf38eae0b35d8f9a755ee` |
| `development/research/source-evidence/20261029/prediction-teleport/26-1-aliases-audit.md` | `c2031792ed78522cef14f43115f0415730132f41a137fb50adab2c394e3179a3` |

The assigned source reports identify the exact Java source hashes as follows:

| Release / namespace / source | SHA-256 |
|---|---|
| 1.21.11 / Mojmap / `ClientPacketListener.java` | `f1ebdb54d717266c894c87381c98bad101980d252de5bae6ca55c37aece1732e` |
| 1.21.11 / Mojmap / `ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` |
| 1.21.11 / Mojmap / `prediction/BlockStatePredictionHandler.java` | `39914e30a1c4cc8a5a6e53a7032e58153c9bd7000bb7def126229ab4cf854728` |
| 26.1.2 / unobfuscated / `ClientPacketListener.java` | `eb70d05e4f8429341fc41823eae2cc662560efa1e44c8b729db5477501718d82` |
| 26.1.2 / unobfuscated / `ClientLevel.java` | `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7` |
| 26.1.2 / unobfuscated / `prediction/BlockStatePredictionHandler.java` | `3436c1a040b55036fc1b1c35bb99fe811e799d013503d65143f3edb77cd83a21` |

The alias evidence reports those seven relevant Java files as byte-identical in 26.1, 26.1.1 and 26.1.2. No product edits, builds, tests, or runtime launches were performed.
