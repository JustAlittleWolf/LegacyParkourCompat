# Independent blind source audit verdict: 1.12 → 1.12.1

## Frozen input and exact source identity

Audited only the bounded report at `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-112-1121-glide-look-dismount/report.md`.

- Frozen report SHA-256: `1db94cf0b7a1c7e85abffe2fe3aec9d8536f2a25215a81fbb0788a750bd7931e`.
- Canonical active source roots: `decompiled_minecraft/1.12/ornithe-feather/` and `decompiled_minecraft/1.12.1/ornithe-feather/`.
- Hidden provenance-manifest SHA-256, 1.12: `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- Hidden provenance-manifest SHA-256, 1.12.1: `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.

Both active roots contain `.decompile-provenance.json`. No separate readiness marker was present; readiness was established from the hidden manifests, required source files, and their matching recorded digests. Retained `.ornithe-feather.previous-*` directories are separate backups and were not used.

The manifests identify the stable Feather Gen2 mapping build 2 for each exact release. Each local metadata JSON, mapping archive, and adjacent `.pin.json` matches the digest recorded in the report and provenance inputs:

| Release | Feather metadata SHA-256 | Metadata pin SHA-256 | Feather build.2 archive SHA-256 | Archive pin SHA-256 |
|---|---|---|---|---|
| 1.12 | `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4` | `76a51caf10558eb4e29fbcda24e60d38fb70b338d7af721e55696700b6467aa0` | `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` | `2ba8c39f328203c18f8ff59136c416d29505fbdf1dd4bc745e1d4e264d52d24d` |
| 1.12.1 | `1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b` | `dcc069db752e88c44e9561d219f3e71745d273151d723ce3287e5bdb0467546f` | `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` | `b5e66f4d33eefea63eca72a27b574da23c31d3f4b3e4cdd3e2f8a920c4f4b890` |

The archive pins point to `feather-gen2:<release>+build.2`; the metadata records stable build 2 for the matching `gameVersion`. For each release, all five source files below were present and each actual SHA-256 matched its hidden provenance `sources` entry:

| Source path | 1.12 SHA-256 | 1.12.1 SHA-256 |
|---|---|---|
| `net/minecraft/world/World.java` | `f7ab7a34d456e720e7ee4f34fbd0eb10f7f96151596c8c615ab2edef05a884d8` | `d182122a9b052f57803672c21b0941cb7e7d41ed756cd0a4ea8c0858684c8259` |
| `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` | `4dab93ea3178a28c616947004ecdc514e6542edca8c62ef83f686ccf6b2ce640` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |

## Independent verdicts

### Already-active, dry client Elytra movement and raw yaw — ACCEPT, no difference found

For the bounded state requested (fall-flying flag already active, valid chest-slot Elytra, ordinary player not ability-flying, and neither water nor lava), the two versions have identical movement gates and glide-body order. `LivingEntity.moveRelative` checks local control or logical-side movement, the water/lava gates, then `isFallFlying`; applies the fall-distance condition, lift/dive/steering arithmetic, drag, and `move(SELF, ...)` in the same order in both versions. `PlayerEntity.moveRelative` delegates to this method on the non-ability-flight path.

`Entity.getLookVector()` calls the raw `getRotationVector(this.pitch, this.yaw)` overload (`Entity.java:1690–1692`); its trigonometric operations are at `Entity.java:1239–1245`. It therefore uses current entity yaw directly, not the interpolated head-yaw method (`LivingEntity.java:1960–1968`). The relevant `LivingEntity.java` and `Entity.java` files are byte-identical between releases. The distinct `PlayerEntity.java` files have matching normalized selected excerpts: `moveRelative` lines 1385–1404 SHA-256 `3ad36639775e587367b48c7e3cdf990c57fdfba9b0e0bedd8783cd8f262722c9`.

Refs: `LivingEntity.java:1425–1477, 1842–1847, 1854–1873`; `PlayerEntity.java:1385–1404`; `Entity.java:1239–1245, 1690–1692`; `LivingEntity.java:1960–1968`. This accepts only the exact paired source claim; it is not a runtime trajectory or full-pair verdict.

### Ordinary local non-Boat/non-Horse dismount writer and side gate — ACCEPT AS A SOURCE OBSERVATION; NOT A CLIENT HISTORICAL OPERATION

The bounded report correctly traces the ordinary sneak dismount route and its position writer, and the relevant bodies are identical in both versions. The decisive gates are server-only: `PlayerEntity.rideTick()` calls `stopRiding()` for sneak+riding only when `!world.isClient` (`PlayerEntity.java:374–395`), and `LivingEntity.stopRiding()` calls `dismountRider(oldMount)` only when detached and `!world.isClient` (`LivingEntity.java:1907–1914`). For non-Boat/non-`HorseBaseEntity` mounts, `dismountRider` routes through `Entity.teleport`, then `setPositionAndAngles` and `setPosition` (`LivingEntity.java:1321–1365`; `Entity.java:2181–2185, 1122–1135, 298–305`).

This establishes the authoritative server-side vanilla writer route, not a client historical-physics path. The ordinary local client does not enter the sneak dismount branch or invoke this `dismountRider` placement route through it. Do not implement or replay this server-only placement as historical client movement. The source audit did not judge whether any candidate placement is collision-clear or eligible; that remains the external B geometry condition, intentionally untouched.

Refs: `PlayerEntity.java:374–395`; `LivingEntity.java:1321–1365, 1907–1914`; `Entity.java:1648–1654, 2181–2185, 1122–1135, 298–305`; `World.java:1408–1434`. The selected `rideTick` excerpt SHA-256 is `189a9427c03bcc32037cd159a9036875ce6c206ec2fad7eac175fada0653897b` in both versions; the `World.tickEntity` excerpt SHA-256 is `756f7c935f77ef0bbba347b4357c440d6755b14f1d0a8c93d1464cdfb1a8116d` in both versions. `LivingEntity.java` and `Entity.java` are identical across the pair.

## Acceptance boundary

The report's bounded no-difference conclusion is accepted for these two independently checked source slices, with the dismount operation explicitly classified as server-side-only for the ordinary local sneaking route. No candidate dismount geometry, complete pair inventory, intermediate 1.12.1 patch boundary, implementation, runtime parity, or broader tick coverage is accepted by this verdict.

No wiki, product implementation, product Git history, network source, generation, build, test, or game launch was consulted or performed.
