# Independent source audit: 1.21.1 → 1.21.2 walk-distance path

## Verdict

**Accept the bounded no in-scope movement candidate conclusion.** The source-visible `walkDist`/`walkDistO` update conditions and storage location change, but the complete canonical Java reference scan shows that the 1.21.1 combat/sweep consumer is removed in 1.21.2, and the remaining references are rendering or counter writes/snapshots. Neither local movement predicates nor the local auto-jump path reads the counter. This does not close the full release pair or establish runtime parity.

## Input and source identity

The assigned closure is `.native-source/20261028/frontier-c/1211-1212-walkdist-consumers/closure.md`; its on-disk SHA-256 is `4a350b292f54cf1d2cc3c4c06ec87af72fdafbdfcec91a63f2088784226e248f`, matching the requested input.

Canonical read-only root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

- **1.21.1 Mojmap:** Release-parent `.provenance.json`, `.ready.json`, and `.sources.sha256` are present. The independently recomputed provenance hash is `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`; ready marker hash is `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310`; source-manifest hash is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. The ready record identifies version 1.21.1, Mojmap, and 5,363 Java sources.
- **1.21.2 Mojmap:** Release parent contains only the mapping directory and has no separate ready/provenance/source-manifest sidecars. The mapping-root `.decompile-provenance.json` is present and hashes to `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`. Its source map and actual tree each contain 5,654 Java paths. Its inputs pin version metadata `https://piston-meta.mojang.com/v1/packages/c14db17c663edf3d7a698488263a21b1df6d02af/1.21.2.json` at SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`, client JAR SHA-256 `613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c`, and Mojmap `client.txt` SHA-256 `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. Raw artifacts were not rehashed in this audit.

I recalculated all eight selected Java-file SHA-256 values from both canonical trees and matched each against the 1.21.1 parent source manifest and the 1.21.2 embedded provenance map. All 16 selected entries match. The independently searched reference set for `walkDist`/`walkDistO` is complete for the checked Java trees.

## Independent source trace

### 1.21.1 writer and gates

`Entity` declares `walkDist` and `walkDistO`; `Entity.tick` copies `walkDist` into `walkDistO`. In `Entity.move`, the counter update occurs after collision resolution and only under `getMovementEmission().emitsAnything() && !isPassenger()`. The added value is `(float) resolved.horizontalDistance() * 0.6F`. `Player.getMovementEmission()` returns `NONE` while flying or while on ground and discrete, otherwise `ALL`; the method is the same in 1.21.2.

### 1.21.2 writer and gates

The fields move from `Entity` to `AbstractClientPlayer`. `AbstractClientPlayer.tick` snapshots `walkDistO` before `super.tick`. The generic entity movement-emission helper still updates `moveDist` and `flyDist`, but no longer updates `walkDist`. Instead, `LocalPlayer.move` captures starting X/Z, calls `super.move`, casts the actual resulting X/Z displacement to floats, invokes `updateAutoJump`, then increments `walkDist` by `Mth.length(dx, dz) * 0.6F`. This local-player override has no movement-emission or passenger gate.

### Complete consumers

The full-tree search found these `walkDist`/`walkDistO` references:

- 1.21.1: declarations/snapshot; the `Entity.move` writer; `GameRenderer.bobView`; `CapeLayer`; and `Player.attack`'s sweep-attack gate.
- 1.21.2: declarations/snapshot in `AbstractClientPlayer`; the `LocalPlayer.move` writer; `GameRenderer.bobView`; and `PlayerRenderer`.

The old attack gate reads `walkDist - walkDistO` while checking grounded, fully charged, sword, nonsprinting attack conditions. In 1.21.2, the corresponding combat gate uses `getKnownMovement().horizontalDistanceSqr()` and a sword item tag instead. It no longer reads the counter. Attack/sweep eligibility and damage/combat resolution are excluded from this movement scope.

Neither version's `LocalPlayer.updateAutoJump` or local movement predicates read the counter; the complete reference search confirms this. The 1.21.2 `getKnownMovement()` replacement used by the new combat gate also does not read it. The changed counter therefore has rendering consumers and an old excluded combat consumer, but no remaining in-scope local movement consumer in the examined trees.

## Limits

This verdict concerns only the source-visible counter path. It does not claim identical rendered bob or animation, classify combat behavior, establish a trajectory, close resource/tag or collision dependencies for the release pair, or freeze 1.21.1 → 1.21.2. No product files, tests, builds, or runtime processes were examined or changed.
