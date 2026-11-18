# Independent audit verdict: 1.9.2–1.9.3 local movement packet selection

**Verdict: accepted for the bounded claim; no source difference found.** The frozen claim is the assigned ordinary local-player packet selection path and directly checked local effects of `handlePlayerMove`. This verdict does not certify pair-wide movement coverage or runtime parity.

## Immutable input and provenance check

Frozen candidate report SHA-256: `61ef1f4c5806f45c7bb34bc0936d12fb0774676b759e88d3b35734ad55cf9869` (verified against the report bytes). Both exact source trees are `decompiled_minecraft/<version>/ornithe-feather/`, using Ornithe Feather Gen2 build 2 for the named release. For each version, I parsed `.decompile-provenance.json`, checked its file hash against the reported identity, and verified each cited Java source's actual file SHA-256 against that manifest's `sources` entry. The manifest records the mapping and metadata artifact inputs, exact client and release-metadata inputs, Vineflower 1.12.0 hash, pipeline identity and runtime stated below.

| Release | Manifest SHA-256 | Actual source SHA-256 (also manifest `sources` value) | Manifest-pinned client / Feather / Feather metadata SHA-256 |
|---|---|---|---|
| 1.9.2, Feather `1.9.2+build.2` | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `LocalClientPlayerEntity.java` `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb`; `ClientPlayNetworkHandler.java` `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6` | client `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`; Feather `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`; metadata `5bad5e67d0870fec69bbab8fcae8d0525a274668bb472eb09d1552359d08edd1` |
| 1.9.3, Feather `1.9.3+build.2` | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `LocalClientPlayerEntity.java` `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`; `ClientPlayNetworkHandler.java` `5a2fc039b81ec0e77df3354826aaaf00dfa6e0d1296cf111864900932e811a4c` | client `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b`; Feather `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; metadata `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985` |

Both manifests record Vineflower `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`, pipeline `lpc-decompile-v1;threads=1;generic=true;ascii=true;synthetic=remove;indent=4;inner-warnings=false;runtime=false;prefix=net/minecraft,com/mojang;remap=ignoreConflicts,renameInvalidLocals,rebuildSourceFilenames,fixPackageAccess`, and `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64`. Input hashes establish the manifests' pinned provenance; this audit did not redownload or independently verify the upstream artifacts.

## Trace and comparison

- **Tick gate / ordinary sender:** In 1.9.2 `LocalClientPlayerEntity.tick()` calls `super.tick()` only under `world.isChunkLoaded(new BlockPos(x, 0.0, z))`; its non-riding branch then calls `sendMovementToServer()`. 1.9.3 has the same branch and order. The riding tick sends angles/input and potentially a vehicle packet instead; that path is excluded from this ordinary-player verdict.
- **Action and movement gates:** `sendMovementToServer()` sends sprint and sneak action changes before the `isCamera()` gate. Only inside that gate does it compare movement state with the remembered sender baseline. Position uses `x`, `getShape().minY`, `z` against `sentX/Y/Z`; the squared delta test is `> 9.0E-4`, or the incremented position timer is `>= 20`. Rotation uses exact nonzero yaw/pitch deltas. The method selects combined position/angles, position-only, angles-only, or on-ground-only in the same order in both versions. Position baseline/timer writes occur only when position is selected; angle baseline writes occur only on rotation change; `sentOnGround` is written at the end of the camera-gated block.
- **Exact method check:** Extracting each complete member from its declaration through closing brace yielded equal UTF-8 text and SHA-256 `cc94a93838f95ebe902dafebf47da5e67e40cb420f7ca1fe302fda7a10526944` for `sendMovementToServer()` on both releases.
- **Legitimate correction response:** `ClientPlayNetworkHandler.handlePlayerMove(PlayerMoveS2CPacket)` is identical in both releases. It applies the correction through `updatePositionAndAngles`, sends `AcceptTeleportC2SPacket`, then sends a local position-and-angles movement response using the resulting player state and `false` on-ground. On the first response only, it copies player coordinates to `lastX/Y/Z`, marks `started`, and closes the screen. The handler does not write `sentX/Y/Z`, `sentYaw/Pitch`, `sentOnGround`, or `ticksSinceSentPosition`; inspection of references in both cited classes found those sender baselines read/written only in `LocalClientPlayerEntity`. Thus this correction path does not reset those baselines in either release. No correction arithmetic, velocity parity, server behavior, crafted packet, or physical replay is assessed here.
- **Exact method check:** The complete handler member text is equal across releases; UTF-8 SHA-256 is `0938d46b9efb4eb3eca4ec195b747c09c43156079ddcb926aff87a5ad79a381c` on each side.

## Exact references

- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `tick()` 142–156; `sendMovementToServer()` 158–219.
- `decompiled_minecraft/1.9.3/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `tick()` 143–157; `sendMovementToServer()` 159–220.
- `decompiled_minecraft/1.9.2/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`: `handlePlayerMove(PlayerMoveS2CPacket)` 545–594.
- `decompiled_minecraft/1.9.3/ornithe-feather/net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`: `handlePlayerMove(PlayerMoveS2CPacket)` 546–595.

Checked source statements above are byte-for-byte identical in the compared members. The result closes only this bounded claim for this exact adjacent pair.
