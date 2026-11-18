# Independent audit: 1.11.2 → 1.12 Llama passenger Y

## Verdict

**Confirm the bounded no-difference claim** for the adult-default Llama passenger placement slices in the exact local Ornithe Feather 1.11.2 and 1.12 source trees. The callback plus `getMountHeight` bodies are byte-identical as extracted (UTF-8/LF excerpt SHA-256 `5559153ddc391770633130d51b261b72b3e025812bf49dc4c4061a04195ff5b5`). This is not full movement-pair coverage and does not establish 1.12.1/1.12.2 behavior or an all-1.12.x applicability boundary.

The prior report's provenance caveat needs narrowing. Current 1.11.2 raw client bytes and the hidden `.decompile-provenance.json` are absent, but its adjacent campaign readiness, provenance, source-hash, artifact-hash and verification records are present. All 1,921 current source files match that local source manifest. Therefore the local expected source digest is verifiable; the historical raw client cannot be rehashed from this checkout. Do not conflate those two facts. 1.12 has the hidden provenance manifest, all its recorded source hashes match locally, and the pinned raw client is present and matches its recorded digest. The 1.12 tree has no adjacent campaign `ready.json`/`provenance.json`/`sources.sha256` files.

## Frozen input and publication evidence

The audited draft is `c-llama-1112-112-position-intermediate/report.md`, SHA-256 `9c36290c7bddb31cf6d4941c238439582cbfcd7de64c366049957042030756b3`.

For **1.11.2**, the parent records identify version `1.11.2`, namespace `ornithe-feather`, and the source publication:

- `ornithe-feather.ready.json`: SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; status `ready`; 1,921 source files; its recorded source-manifest SHA matches the file below; its expected client SHA-256 is `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`.
- `ornithe-feather.sources.sha256`: SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; all 1,921 listed source paths exist and match their listed SHA-256 values, including every inspected file.
- `ornithe-feather.provenance.json`: SHA-256 `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; records the exact 1.11.2 Feather build.2 batch, pinned mapping artifacts, tool versions and preparation run.
- `artifacts.sha256`: SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`; records expected client bytes above and the Feather artifact pins.
- `ornithe-feather.verification.json`: SHA-256 `1ff7d450309db002c7b9e42319f933ea6db75f0622d45996b3674ecb7e5c8fb4`; records 1,921/1,921 identical source files, zero missing source files, and zero raw-input differences in that prior verification. It separately records a regenerated derived remap-jar hash difference; that does not change the source-tree digest.

There is no `decompiled_minecraft/1.11.2/ornithe-feather/.decompile-provenance.json`, no `.minecraft-decompile-cache/1.11.2/`, and the readiness record's old campaign output path is absent. Thus the expected raw-client digest is retained as metadata, not a currently present raw archive that this audit could hash anew. The local source tree remains completely bound to its adjacent source manifest.

For **1.12**, no adjacent campaign readiness/provenance/source-manifest files are present. The hidden `decompiled_minecraft/1.12/ornithe-feather/.decompile-provenance.json` is present (SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`). All recorded source hashes match the current tree, including the eight inspected files below. Its pinned raw client exists at `.minecraft-decompile-cache/1.12/client.jar`; actual and recorded SHA-256 are both `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`. The manifest also pins Feather `1.12+build.2` merged artifact `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` and Feather metadata `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4`.

Inspected source-file SHA-256 values (all match the applicable manifest):

| Relative source path | 1.11.2 | 1.12 |
|---|---|---|
| `net/minecraft/entity/living/mob/passive/animal/LlamaEntity.java` | `1577cf77a5c1d0e47dde0e13327dc333fcd65aa86c7e9073f49b2c678f0257b1` | `487c7209a69391f1a19dce5fbdf34d609d2e9bc53c47b04a225e9c0d0c93cf7e` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| `net/minecraft/world/World.java` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` |

## Callback and numeric trace

Both `LlamaEntity` files construct the default entity with `setSize(0.9F, 1.87F)` at lines 60–62. The only Llama rider-position override is `updateRiderPositon` at lines 144–151; it is guarded by `hasPassenger(rider)`. It calculates float `f` and `g` from cosine/sine of `bodyYaw * (float)(PI / 180.0)`, declares unused float `h = 0.3F`, then calls `setPosition` with X `this.x + 0.3F * g`, Y `this.y + this.getMountHeight() + rider.getRideHeight()`, and Z `this.z - 0.3F * f`. The override at lines 154–156 returns `this.height * 0.67`; it does not call the `HorseBaseEntity` rider callback.

For an adult default Llama carrying a `PlayerEntity`, `height` is the float established by `1.87F`; multiplication by the double literal `0.67` promotes it to double. `PlayerEntity.getRideHeight()` returns double `-0.35` (1.11.2 lines 948–950; 1.12 lines 943–945). The sole direct Y expression is left-associative double addition: `llama.y + ((double) 1.87F * 0.67) + (-0.35)`, approximately `llama.y + 0.902900003194809`. There is no changed cast, rounding point, gate or operation order in the paired callback.

The local-player route is `World`'s in-chunk riding branch (`World.java` 1418–1425 / 1427–1434) → virtual `rideTick()` (`LocalClientPlayerEntity.java` 798–806 / 820–828) → `PlayerEntity.rideTick()` (`PlayerEntity.java` 379–398 / 375–394) → `Entity.rideTick()` (`Entity.java` 1556–1568 / 1582–1594), which zeroes rider velocity, ticks it, then dispatches `mount.updateRiderPositon(this)` if still riding. Virtual dispatch reaches Llama's guarded override. Boat input handling after the local superclass call does not alter the Llama placement path. The callback source itself has no logical-client gate; project implementation must still keep historical movement client-only.

## Remaining bounds

This verdict closes only the compared default-adult Llama/Player placement and its immediate local tick dispatch for exact 1.11.2 and 1.12 source. It does not close the 1.12.1/1.12.2 source boundary, every mount callback, full tick inventory, independent full-pair coverage audit, or runtime parity. No product implementation, tests, builds, source generation or runtime launch was performed.
