# Independent bed-boundary source audit

Verdict: **The bed-bounce source claim is supported, with the stable-release boundary narrowed to 1.12 among the audited releases.** The supplied candidate correctly describes the endpoint behavior and callback gate. Its statement that the introduction boundary remains unknown is too broad when read alongside the supplied four-release evidence: the override is absent in 1.11.2 and present by 1.12, then unchanged in 1.12.1 and 1.12.2. This establishes 1.12 as the earliest inspected stable release with the behavior, not its possible earlier pre-release history or an uninspected source state.

## Immutable input identities

- Evidence: `.native-source/20261030/1112-1122-pool31/bed-boundary/evidence.md`, SHA-256 `7c0ae4a288100fb23f8fb0bb6229f8431fa8c60152833763688b8fb1d4dc5352` (recomputed; match).
- Candidate: `.native-source/20261030/1112-1122-pool31/collision/bed-bounce-on-landing.md`, SHA-256 `065582cbbf16452d55a350c01f2a9b2ef822e8031266059d6539829ce200f621` (recomputed; match).
- Canonical source root inspected read-only: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather`.
- All four source trees identify the namespace as Ornithe Feather / Feather Gen2. Exact release tree provenance is either parent release manifests (1.11.2, 1.12.2) or the embedded `.decompile-provenance.json` (1.12, 1.12.1).

## Provenance and source-file verification

For 1.11.2, the parent records exact release `1.11.2`, mapping option `feather`, Feather Gen2 `1.11.2+build.2`, and mapping artifacts `feather-gen2-1.11.2+build.2-mergedv2.jar` (recorded SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`) and `.tiny` (`4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6`). Parent provenance SHA-256 is `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; source manifest SHA-256 is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, matching the ready record. The exact callback/caller sources re-hash to the corresponding manifest entries.

For 1.12 and 1.12.1, the embedded provenance files re-hash to `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` and `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. They record Feather Gen2 `1.12+build.2` mapping jar SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` and metadata SHA-256 `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`; and Feather Gen2 `1.12.1+build.2` jar SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` and metadata SHA-256 `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`. Their provenance also records exact Mojang client and version-metadata input hashes. I recomputed the embedded source hashes for `Block.java`, `BedBlock.java`, `Entity.java`, and `LocalClientPlayerEntity.java`; all four files match in each release.

For 1.12.2, parent provenance SHA-256 is `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`. It records Feather Gen2 `1.12.2+build.2` mapping jar SHA-256 `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` and `.tiny` SHA-256 `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. The source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` matches its ready record and all four inspected direct-source hashes match the manifest entries.

Direct source hashes (`Block.java` / `BedBlock.java` / `Entity.java` / `LocalClientPlayerEntity.java`):

| Release | `Block.java` | `BedBlock.java` | `Entity.java` | `LocalClientPlayerEntity.java` |
|---|---|---|---|---|
| 1.11.2 | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| 1.12 | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| 1.12.1 | same as 1.12 | same as 1.12 | same as 1.12 | same as 1.12 |
| 1.12.2 | same as 1.12 | same as 1.12 | same as 1.12 | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |

`Block.java`, `BedBlock.java`, and `Entity.java` are byte-identical across 1.12 / 1.12.1 / 1.12.2. `LocalClientPlayerEntity.java` is byte-identical in 1.12 and 1.12.1; 1.12.2 differs elsewhere while retaining the inspected tick/move delegation.

## Source comparison and reachable player path

- **1.11.2:** `Block.beforeCollision(World, Entity)` at `Block.java:624–626` sets `velocityY = 0.0`. `BedBlock.java` has no `beforeCollision` method. In `Entity.move`, after collision resolution and `setPositionFromShape`, it samples `(floor(x), floor(y - 0.2F), floor(z))`; if that is air it may select the block below only for fence, wall, or fence gate. The callback is invoked at `Entity.java:723–725` when the original requested vertical delta `o` differs from resolved vertical delta `y`. No logical-side test guards this callback branch.
- **1.12 / 1.12.1 / 1.12.2:** base `Block.beforeCollision` remains `velocityY = 0.0` (`Block.java:643–645`). `BedBlock.beforeCollision` (`BedBlock.java:141–150`) delegates to base when sneaking. Otherwise it only acts for `velocityY < 0.0`, assigning `-velocityY * 0.66F`; the subsequent `*= 0.8` applies only to entities not `LivingEntity`. A player is a `LivingEntity`, so that extra multiplier does not apply. No logical-side guard appears in the override.
- **Call order:** `Entity.move` first resolves the requested delta against shapes, updates position and collision flags, selects the support block, and performs `checkFallDamage`; it then invokes `beforeCollision` under `o != y`. In the inspected method body this callback path does not first zero `velocityY`, so the bed override reads the falling velocity and can turn it positive. This conclusion is scoped to this callback path.
- **Client player reachability:** in each tree `LocalClientPlayerEntity extends ClientPlayerEntity extends PlayerEntity extends LivingEntity extends Entity`. Local `tick()` calls `super.tick()` (1.11.2 `:159–162`; 1.12/1.12.1 `:164–167`; 1.12.2 `:165–168`). The local player and player overrides of `mobTick` delegate to `super.mobTick`, and living movement branches call virtual `this.move(...)`; local `move(...)` delegates to `super.move(...)` (1.11.2 `LocalClientPlayerEntity.java:824–829`, 1.12/1.12.1 `:841–846`, 1.12.2 `:846–851`). The local player is a client entity; the `Entity.move` callback dispatch is not restricted to a logical side.

Thus a non-sneaking player reaching a bed while `velocityY < 0.0` can leave the collision with positive/upward vertical velocity beginning in 1.12; in 1.11.2 the base callback sets vertical velocity to zero. Sneaking preserves the zeroing behavior. The callback's vertical-collision gate is not itself a falling-only test; the bed's negative-velocity condition supplies the falling condition. No block-state mutation is implicated.

## Boundary and limitations

Among the four audited stable releases, **1.12 is the first present**: 1.11.2 lacks the bed override; 1.12 contains it; 1.12.1 and 1.12.2 have the same callback and caller source as 1.12. This narrows the candidate's boundary statement to absent at 1.11.2 / present by 1.12. It does not establish history in pre-releases or uninspected source artifacts, nor a commit-level introduction date.

The raw artifact/cache and recorded verification staging directories for 1.11.2 and 1.12.2 are absent in this environment. Their existing parent verification JSON reports all published source files and raw source inputs identical in a later exact re-decompile, with one derived `client-ornithe-feather.jar` remap-output hash difference per release and an explanation that it is a regenerated TinyRemapper intermediate. I verified the JSON/manifests and current source files, but did not independently rerun those verifications. For 1.12 and 1.12.1 there are no current release-parent ready/source/artifact manifests or raw mapping/client artifacts; the in-tree provenance records and its source hashes are verifiable, but mapping/client artifact byte identities remain recorded provenance claims rather than re-hashed raw bytes in this environment. These limits bound artifact reproducibility, not inspection of the current hashed Java files.

Audit phase was limited to the supplied evidence/candidate and canonical source/provenance files. No wiki, product implementation, product history, source generation, build, tests, or launch were used.
