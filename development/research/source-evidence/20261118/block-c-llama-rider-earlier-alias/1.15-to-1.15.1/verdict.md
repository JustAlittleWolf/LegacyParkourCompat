# Independent audit: Llama local-player Y placement, 1.15 → 1.15.1

## Verdict

**Accepted: no difference in the bounded direct local-player Y placement operation.** The independently checked `Llama.positionRider(Entity)` and `getRideHeight()` bodies are intact and byte-identical across the two exact source trees. This verdict does not establish full-pair coverage, a patch boundary, or runtime parity.

For a default adult Llama or TraderLlama (registered height `1.87F`), a local Player's direct Y argument is `llama.getY() + (llama.getBbHeight() * 0.67) + (-0.35)`. `getBbHeight()` returns `float`; unsuffixed `0.67` promotes that operand to `double`, and the enclosing additions are left-associative `double` operations. Player `getRidingHeight()` is `double` `-0.35`; there is no intervening cast or rounding point. These facts and operation order are identical in both releases.

The actual path is `Entity.rideTick()` → virtual `vehicle.positionRider(this)` → Llama's override, whose `hasPassenger` gate guards the direct `entity.setPos(...)` call. Llama does not invoke `super.positionRider`; Entity's generic `MoveCallback` overload is therefore not the placement writer on this path. `LocalPlayer.rideTick()` delegates to Player, whose server-only stop branch is false on the logical client. `ClientLevel.tickPassenger` reaches `rideTick()` for a player passenger when the links are valid and `inChunk`. An adult Llama can enter `doPlayerRide`; TraderLlama inherits the placement override and only blocks starting a ride while leashed to a Wandering Trader.

## Exact evidence and hashes

Canonical read-only roots are `decompiled_minecraft/1.15/mojmap/` and `decompiled_minecraft/1.15.1/mojmap/`. The stale historical `sourceRoot` strings in the provenance records were not used to locate evidence. Both roots contain ready markers with exact IDs `1.15` and `1.15.1`, mapping `mojmap`, and status `ready`; both provenance records identify `prep-1.15-1.15.1-mojmap-2026-10-09-r1`. The actual source-manifest SHA-256 values match each ready marker and provenance record: 1.15 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; 1.15.1 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`. The `artifacts.sha256` file hashes also match the ready markers: 1.15 `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae`; 1.15.1 `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb`.

Relevant paired file SHA-256 values match both the corresponding source-manifest entries and actual local file bytes:

| Exact Mojmap source path | 1.15 and 1.15.1 SHA-256 | Relevant lines |
|---|---|---|
| `net/minecraft/world/entity/animal/horse/Llama.java` | `bbe98226c5afaddce8ca7458c721fbefff592cc75e02bc261d6b440ca6877f19` | 149–160 |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | 1544–1571, 2662–2664 |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | 459–477, 986–988 |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | 825–826 |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` | 164–204 |
| `net/minecraft/world/entity/EntityType.java` | `9a3ac5b01ebf9cb783e5b7073ae227ee2f58dd96437936f6aa21134d72a8d818` | 208, 285–287 |
| `net/minecraft/world/entity/animal/horse/AbstractChestedHorse.java` | `0d875b09b0218c120abdad559dc87a56255f6e1c072456560e669bcaf9f93d47` | 56–58, 139–195 |
| `net/minecraft/world/entity/animal/horse/AbstractHorse.java` | `a967d0834d3c28080725a8b639d2c8c182ee6a7209a361d462fb0225a2836e6d` | 476–484, 899–917 |
| `net/minecraft/world/entity/animal/horse/TraderLlama.java` | `495d65da1c1903ba769793ee4dbccd6248ef96137c1709af6daa00c563c03dd4` | 22, 61–66 |

The ready/provenance records name exact version metadata IDs and record the corresponding official client-mapping hashes: 1.15 metadata `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785`, mappings `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`; 1.15.1 metadata `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598`, mappings `f46186d0ed9d17e073e16ae2c98675dbbc70b4a29495700837aa5de9c1b42c34`. The checked `artifacts.sha256` entries agree with those client-mapping and client-JAR hashes. Raw historical artifact files were not present at the recorded cache paths, so those input bytes were not re-hashed during this audit; exact identity here is supported by the canonical ready/provenance records, artifact manifest, and the verified source bytes/manifests.

## Scope fence

This audit is limited to direct Y placement of a local player riding an ordinary adult/default Llama or eligible TraderLlama and the source guards needed to reach that call. It makes no claim about other actors, other passengers or carriers, Llama/vehicle physics, unrelated horse-family placement, complete pair coverage, or runtime parity.

## Immutable inputs

- Frozen report: `c-llama-115-1151-position-intermediate/report.md`
- Frozen report SHA-256: `f2af75e9427b5a8177f4b54a82ec5b66f505753c0e74f8a9de563abf04469cea`