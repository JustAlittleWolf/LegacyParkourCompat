# Swimming pose and player dimensions source comparison

TERMINAL: COMPLETED — outer readiness/provenance sidecars establish the exact release and mapping identities, and every bounded source file hash matches its outer source manifest. The earlier r1 status is superseded: it checked only for nested manifests and missed the canonical sibling sidecars. The pair observations below now have verified local source/input identity. Remaining gate: the precise release that changed crouching dimensions is unknown between 1.13.2 and 1.14.4.

## Scope and identity

- Host/execution: Windows, PowerShell 7.6.9; repository `D:\Javastuff\LegacyParkourCompat`.
- Canonical sources: `decompiled_minecraft/`; all evidence is read-only. No product sources, wiki inventories, Git history, generated sources, tests or runtime were consulted.
- Requested releases are present under `1.12.2/ornithe-feather`, `1.13/ornithe-feather`, `1.13.1/ornithe-feather`, `1.13.2/ornithe-feather`, and `1.14.4/mojmap`. Their outer sibling `.provenance.json`, `.ready.json`, and `.sources.sha256` sidecars establish the exact source and mapping identities, as detailed in the R2 resolution section.
- The source fingerprints below are byte hashes of the local files and are cross-checked against the upstream-input identity via the sibling source manifests described in R2:

| Local source file | SHA-256 |
|---|---|
| `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` | `E4E0FDBE07A7D0A0AE4A70CBB6739A287C9D045A4A12B409895C220B5D91FE1E` |
| `1.13/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` | `4A57CE65405436AEB211D7A04A9A9F6AE1A661D033FD3BA2424909BFB42B8ACD` |
| `1.13.1/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` | `4C1B14B4727116071B1BA9E3EEAE84B8077F3D767E10E49B6A68A2556DA32272` |
| `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` | `4ED22F6C5A3C55D67EED782070AC722201DF4D624ADBC90779F1FD29C2876633` |
| `1.13.2/ornithe-feather/net/minecraft/entity/Entity.java` | `1D6EC8B80F74635401745C2C027BF36555C85348CA5693764F2668363B17D269` |
| `1.14.4/mojmap/net/minecraft/world/entity/player/Player.java` | `E9CE5EB18C5581FFE2B610B273FFD85786587A00BA853E5DBA0AC96593622B12` |
| `1.14.4/mojmap/net/minecraft/world/entity/Entity.java` | `31C6BE42D165102D3E6DC4295FDFEF6E8971FC3AEA13F938A5B3A33B91D524A7` |


## R2 source provenance resolution

The canonical identity records are sibling sidecars at `decompiled_minecraft/<version>/<mapping>.*`, not files nested under the mapping directory. Each `.ready.json` says `ready`, names the exact `versionId` and mapping, pins the client artifact SHA-256, and names the expected source-manifest digest. For all five requested release roots, the sibling `.sources.sha256` file SHA-256 equals the digest in `.ready.json`; the SHA-256 of each bounded player/entity Java file below also equals its path entry in that source manifest. The `.provenance.json` identifies mapping namespace and exact mapping run/artifact. The 1.12.2, 1.13.2, and 1.14.4 sidecars also have `verified-identical-source-and-input-artifacts` records; 1.13 and 1.13.1 record exact input and Feather artifact coordinates/hashes in `.provenance.json`. No regeneration was performed.

| Release / mapping | Exact mapping identity | Client SHA-256 | Ready source-manifest SHA-256 | Relevant source member file and verified SHA-256 |
|---|---|---|---|---|
| 1.12.2 / ornithe-feather | Feather Gen2 1.12.2 build.2 mergedv2 JAR + Tiny; artifact hashes in `1.12.2/artifacts.sha256` | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `net/minecraft/entity/living/player/PlayerEntity.java` `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.13 / ornithe-feather | `net.ornithemc:feather-gen2:1.13+build.2`; JAR SHA `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`, Tiny SHA `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84` | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `net/minecraft/entity/living/player/PlayerEntity.java` `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13.1 / ornithe-feather | `net.ornithemc:feather-gen2:1.13.1+build.2`; JAR SHA `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`, Tiny SHA `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14` | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `net/minecraft/entity/living/player/PlayerEntity.java` `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.2 / ornithe-feather | Feather Gen2 1.13.2 build.2 mergedv2 JAR + Tiny; artifact hashes in `1.13.2/artifacts.sha256` | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `net/minecraft/entity/living/player/PlayerEntity.java` `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`; `net/minecraft/entity/Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.14.4 / mojmap | Mojang namespace `mojmap`, exact version 1.14.4; `.verification.json` confirms identical published sources and raw input artifacts | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `net/minecraft/world/entity/player/Player.java` `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dbac96593622b12`; `net/minecraft/world/entity/Entity.java` `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |

Independent checks confirmed all listed files match their source-manifest entries and all five ready-marker source-manifest digests match their sibling `.sources.sha256` files. R1 remains byte-exact and untouched; its SHA-256 is `1588C6248605BA35E2EA95F5B69F20203EF5D8F4878DA58C73F3142E90232B19`.
## Bounded source observations

### 1.12.2 to 1.13

In 1.12.2 `PlayerEntity.updatePlayerPose()` (lines 291–315), the size priority is fall-flying `0.6 × 0.6`, sleeping `0.2 × 0.2`, sneaking `0.6 × 1.65`, otherwise `0.6 × 1.8`. It creates a candidate box and applies `setSize` only when `world.getCollisions(box)` is empty. `getEyeHeight()` (1756–1766) gives sleeping `0.2`, crouching `1.54` (`1.62F - 0.08F`), and fall-flying or an already-`0.6F` body height `0.4`; otherwise `1.62`. Searches of the 1.12.2 `Entity`, `LivingEntity`, and `PlayerEntity` sources found no `isSwimming`/`setSwimming` state or swimming-specific player size/eye branch.

In 1.13 `PlayerEntity.updatePlayerPose()` (333–361), after fall-flying and sleeping, `isSwimming() || isDoingSpinAttack()` selects size `0.6F × 0.6F`; the existing sneak/standing sizes remain `0.6F × 1.65F` and `0.6F × 1.8F`. The candidate-box gate changes to `world.hasNoCollisions(null, box)`, followed by `setSize`. `getEyeHeight()` (1854–1867) gives swimming, fall-flying, or body height `0.6F` an eye height of `0.4F`; the crouch and standing values remain `1.54F` and `1.62F`. Player `isSwimming()` (1814–1816) reads the inherited engine flag, with player gates for flying and spectator. This comparison treats that flag as input and does not analyze how water updates it.

**Provisional movement consequence:** when the pre-existing engine swimming flag is true and the existing collision gate permits the size change, player bounding height becomes `0.6F` and eye height resolves to `0.4F`; when the size change is blocked, the body height is retained, though the eye-height getter still checks `isSwimming()` directly. This is a newly introduced swimming-driven pose/dimension behavior in the 1.13 source, distinct from subsequent edits to older standing/sneaking/fall-flying dimensions.

### 1.13.1 and 1.13.2 witness checks

The corresponding mapped members are `PlayerEntity.updatePlayerPose()` (1.13.1 lines 334–362; 1.13.2 lines 334–362) and `getEyeHeight()` (1.13.1 lines 1859–1872; 1.13.2 lines 1859–1872). Both excerpts retain the 1.13 swimming/spin `0.6F × 0.6F` branch, same collision gate, and same eye-height priorities/constants. No swimming-driven body/eye delta was observed in these checked members. This is an ordinary era-existing witness, but remains provisional because those source trees also lack provenance manifests.

### 1.14.4 structural witness and later existing-dimension change

The 1.14.4 Mojmap player source stores dimensions by pose (`Player.java` 110–118): standing `0.6F × 1.8F`, sleeping from the shared sleeping constant, fall-flying/swimming/spin `0.6F × 0.6F`, sneaking `0.6F × 1.5F`, dying `0.2F × 0.2F`. `getStandingEyeHeight()` (1801–1812) returns `0.4F` for swimming/fall-flying/spin, `1.27F` for sneaking, and `1.62F` by default. `getDimensions(Pose)` (1963–1965) resolves from the pose map.

The entity source (Entity.java 191–212, 297–303, 2265–2289, 2337–2347) stores current dimensions and eye height, tracks a `Pose`, and refreshes bounding box and eye height when pose data changes. Player `updatePlayerPose()` (344–370) sets the pose after `canEnterPose` checks. This is a structural reorganization from the 1.13 direct player `setSize` operation. It keeps swimming dimensions/eye height at `0.6F × 0.6F` / `0.4F`, while the already-existing crouch dimensions change from 1.13's `1.65F` body and `1.54F` eye to 1.14.4's `1.5F` body and `1.27F` eye. The precise patch release where those crouch values changed is not established by these endpoints.

## Gaps and next action

- Obtain source preparation identities/provenance for all compared canonical roots, including exact client artifact, mapping family and mapping build/artifact, pinned decompiler input, and provenance hashes; verify before upgrading these provisional observations to accepted findings.
- The exact intermediate release that changed crouching dimensions/eye height remains unknown within the 1.13.2–1.14.4 interval.
- No inference is made about swimming-state production, fluid sensing/travel, collision-shape providers or other pose paths.