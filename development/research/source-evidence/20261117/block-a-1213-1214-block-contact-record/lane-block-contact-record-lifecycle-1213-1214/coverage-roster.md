# Source coverage roster: 1.21.3 → 1.21.4 block-contact record lifecycle

Host: Windows, PowerShell 7.6.9. Sources consumed read-only from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

## Exact source identities

- Both `mojmap.ready.json` markers report `status=ready`, exact `versionId`, and `mapping=mojmap`.
- Both `mojmap.provenance.json` records name the same exact release and Mojmap namespace.
- `mojmap.sources.sha256` raw SHA-256 matches the corresponding ready marker's `sourceManifestSha256`; every source file listed below was independently hashed and matched its manifest row. Full digests are in `sources.sha256`.
- 1.21.3 source-manifest SHA-256: `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`.
- 1.21.4 source-manifest SHA-256: `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`.
- Marker-file SHA-256 values and source-content hashes are in `sources.sha256`.

## Compared source inventory

| Source member | 1.21.3 | 1.21.4 | Slice reviewed |
|---|---:|---:|---|
| `net/minecraft/world/entity/Entity.java` | 250, 399–404, 619–735, 736–782, 875–910, 1034–1084, 1472–1502, 2721–2732 | 250, 399–408, 627–746, 747–794, 887–926, 1046–1095, 1493–1523, 2749–2760 | movement record storage/creation/consumption; piston gate; position write; tick callback path; teleport clear; `Movement` record declaration |
| `net/minecraft/world/level/BlockGetter.java` | 172–end | 172–end | `boxTraverseBlocks` and `addCollisionsAlongTravel` |
| `net/minecraft/world/phys/AABB.java` | 150–156, 216–232, 275–280 | same | `move`, `expandTowards`, `deflate` helper bodies; source hash is identical across pair |
| `net/minecraft/client/player/LocalPlayer.java` | 92, 189–213, 347–350, 478–480, 873–881 | 93, 191–216, 352–355, 483–485, 903–911 | class/tick reachability, local-player predicate, `move` override |
| `net/minecraft/client/player/AbstractClientPlayer.java` | 17 | 17 | superclass declaration in reachability chain |
| `net/minecraft/world/entity/player/Player.java` | 118, 253–258, 280–282 | 118, 256–261, 283–285 | superclass and tick delegation |
| `net/minecraft/world/entity/LivingEntity.java` | 140, 2418–2448, 2774–2777 | 139, 2429–2459, 2785–2788 | inherited tick, travel then block-effects call/gate |

## Scope boundaries

- No concrete implementation, wiki inventory, product Git history, damage/health behavior, server writer body, or solid collision outcome was examined.
- The exposed `recordMovementThroughBlocks` method is covered as an Entity append operation. Its direct server-side call-site bodies are outside this assignment and were not read.
- No code, tests, runtime, build, decompilation, network, or Git operation was performed.