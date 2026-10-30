# Independent source-only full-freeze audit: 1.8.5–1.8.9

## Verdict

**Qualified same-state movement conclusion: supported. Full-pair freeze: not met.** The composed reports and independent audits show no identified change to the covered client movement implementation across 1.8.5→1.8.6→1.8.7→1.8.8→1.8.9. The raw-archive check below closes the previously open 1.8.8→1.8.9 asset comparison: its sole changed asset is a language/UI entry; blockstates, models and block textures are entrywise identical. These facts support identical movement response when effective player inputs, player/effect state, and movement-relevant received vanilla block/world state are held equal at each tick boundary.

This is not a full-pair freeze. The 1.8.5–1.8.8 evidence is adjacent whole-Java-tree hash pruning plus changed-hunk review and a selected core-class roster. The 1.8.8–1.8.9 audits are endpoint inventories and bounded ordinary input/travel coverage. They do not supply a complete pair-wide inventory of every reachable movement terminal and dependency, independently retraced with paired evidence. The source workflow requires that inventory and independent coverage audit for full-pair freeze. This verdict does not imply trajectory equality when initial worlds or other state producers differ, nor runtime parity.

## Permitted evidence and identities

Read-only canonical roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.8.5,1.8.6,1.8.7,1.8.8,1.8.9}/ornithe-feather/`, exact Ornithe Feather mapping family. Existing independent source manifests bind 1,612 Java files in each root. For 1.8.5–1.8.8 the embedded mapping-root provenance is verified; release-parent provenance files are absent, a publication-marker limitation. For 1.8.9 the ready/provenance/source-manifest markers bind client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` and all 1,612 Java sources.

The recovered `.native-artifacts/pinned-clients/1.8.9.json` declares version ID `1.8.9`, client SHA-1 `3870888a6c3d349d3771a3e9d16c9bf5e076b908`, and size 8,461,484 bytes. The paired `.jar` matches all three values and hashes to SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`, agreeing with the source provenance. The 1.8.8 archive at the known canonical cache path hashes to pinned SHA-256 `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` and is 8,465,313 bytes. Archive comparison was read-only; no archive was copied and no canonical source cache was written.

## Compositional movement inventory and closure status

The source material composes these examined slices across all five releases:

1. **Input production and transfer:** `KeyboardInput.tick`, `Input`, `LocalClientPlayerEntity` tick/AI path, and key binding declarations/conversion. The 1.8.5–1.8.8 corrected roster hashes `Input`, `KeyboardInput`, `ClientPlayerEntity`, and `LocalClientPlayerEntity` identically at all four versions. The 1.8.8–1.8.9 ordinary audit independently verifies the nine movement roots and `KeyBinding`/interaction-manager equality.
2. **Player decisions and travel:** local sprint/jump gates, player jump and relative movement, `LivingEntity` travel, and `Entity.move`. The ordinary slice report/audit directly traces keyboard input through local-player AI, sprint/jump, ground/air travel and position/velocity movement. Corresponding full source files are byte-identical across 1.8.8/1.8.9 and the selected roster is unchanged through 1.8.8.
3. **World/collision providers:** `World`, `ClientWorld`, `Block`, `Blocks`, ladder/liquid/slime/soul-sand, piston base/head, ice, stairs and falling-block source anchors are equal across 1.8.5–1.8.8. The endpoint changed-path audit finds no changed or added/removed `block/`, `entity/`, or `world/` source paths from 1.8.8 to 1.8.9; the bridge audit confirms the ordinary roots. This composes equality for inventoried providers but does not prove that the selected 20-class roster exhausts every provider/dependency.
4. **Direct responses and externally produced state:** the examined Bootstrap change and resource routes are resolved below. The reports do not provide a complete exhaustive paired inventory of every externally invoked player response, callback, collision shape consumer, modifier/effect source, and terminal writer for all four pairs.

Adjacent whole-tree Java evidence composes: all 1,612 paths are present at each endpoint; 1.8.5→1.8.6, .6→.7, and .7→.8 have 21, 16, and 28 changed paths, with independently matching path sets; 1.8.8→.8.9 has 26 changed hashes and no path additions/removals. This is strong pruning evidence, but terminal/dependency coverage remains partial.

## Changed-edge disposition

### 1.8.5 → 1.8.6 (21 changed Java paths)

The `Bootstrap` delta removes a dispenser behavior registered for command-block items. In 1.8.5 the scheduled `DispenserBlock.tick` route calls behavior only on the logical server; the behavior also gates block placement, block-entity NBT copy and neighbor update on `!world.isClient`. It can produce a different future received vanilla block state, but does not write client player input, pose, velocity, box or movement response. `DispenserBlock`, `CommandBlock` and `Block` are byte-identical across the boundary. The related `IBlockSource`/`BlockSource.getBlock()` deletion removes the accessor and its only known call in that removed behavior. Disposition: **closed for equal received block-state response; remains a boundary if the claim is equal initial worlds plus all subsequent server/non-player evolution.**

Other changed paths route to operator-gated block-entity NBT placement, UI/version/resource-pack handling, sign/network/server code, diagnostics or logging. No changed movement/provider path was found.

### 1.8.6 → 1.8.7 (16 changed Java paths)

`ResourcePacks` changes server-pack download naming/retention and reload triggering. Remaining changes route to UI, text/sign serialization, server/network, diagnostics, HTTP downloads or metadata. No movement terminal/provider is changed. Resources across this edge are identical per the audited .5–.8 archive inventory.

### 1.8.7 → 1.8.8 (28 changed Java paths)

`GameOptions` and `ResourcePacks` change compatibility-list persistence, pack selection and reload; resource-pack entry/screen edits are UI. These can change selected client assets, not block states or movement calculations under equal effective input/state. The `Bootstrap` hunk only declines to construct a profile for an empty skull-owner string. `AnvilChunkStorage` changes save progress/error/flush bookkeeping. Other deltas route to rendering, UI, Realms, server/network and diagnostics. No movement terminal/provider changed. The asset comparison across this edge found only the language/UI entry noted below.

### 1.8.8 → 1.8.9 (26 changed Java paths)

Independent endpoint inventory and ordinary movement audit show no changed input/travel root, block/entity/world provider or added/removed Java path. The changed `GameOptions` setting is Realms notifications; `Minecraft` version labels, `MathHelper` HSV local names and command/effect local names preserve covered operations. Remaining changes route to UI, rendering/audio, Realms, book text, command handling, server/network and diagnostics. `CrashReport` changes its release string; the independent ordinary audit recorded its omission from the author prose. No changed hunk alters a movement terminal.

## Resource archive check: 1.8.8 → 1.8.9

Both archives contain 3,085 non-directory `assets/` entries with identical path sets: zero added and zero removed. Sorted `path<TAB>entry-SHA256<LF>` inventory hashes are `cd32b56b8deb945c4b885192671ce8e064a6a194b8df38ad6d8ce77d6a562bd7` (1.8.8) and `febff2c9bdf290c7ea4b45301ec2b409359685653658161ff78bdf07d5ab9b92` (1.8.9). There is exactly one changed asset entry:

| Path | 1.8.8 entry SHA-256 | 1.8.9 entry SHA-256 | Disposition |
|---|---|---|---|
| `assets/minecraft/lang/en_US.lang` | `5df7541d53fdd24ee3e55143a038e61e19f377bdc2568cf5a41d980626cd302e` | `e740e69ba367e7fba7353a82db88a8223a5aafd91c4a0a63536b678dddc0a252` | Text entry only; the 1.8.8 Realms-notifications label is removed. |

Targeted family results (`1.8.8 → 1.8.9; added/removed/content-changed`): blockstates `340→340; 0/0/0`; models `1,595→1,595; 0/0/0`; block textures `382→382; 0/0/0`; shaders `87→87; 0/0/0`; fonts `1→1; 0/0/0`; language `1→1; 0/0/1`; sound-directory entries `0→0`. Thus the 1.8.9 resource gap is closed for these pinned archives. The sole delta is presentation text and cannot alter movement with equal effective input and vanilla world/block state. Combined with the earlier verified 1.8.5–1.8.8 archive inventory (same asset paths except known English resource-pack UI wording; blockstates/models/block textures unchanged), the package-resource route is bounded across the group.

## Freeze constraints and next queue

- Accepted: same effective player movement inputs, player/effect/runtime movement state and same received movement-relevant vanilla world/block state at each tick boundary produce the same source-level movement response for the examined 1.8.5–1.8.9 implementation. No changed source/resource edge identified here reaches those responses under that premise.
- Not accepted as full-pair freeze: exhaustive tick-terminal and dependency inventories, including every direct player response, callback, effect/modifier producer, collision/shape consumer, and block-contact route, are not supplied across all four pairs with paired slices and blind audit.
- Not established: trajectory equality from equal initial worlds if the removed server-side command-block dispenser producer is reachable; intermediate patch introduction versions; runtime parity; profile-switch behavior.
- Next source queue: produce a full movement-terminal/dependency inventory across adjacent 1.8.5–1.8.9, explicitly enumerate examined and unexamined slices, bind terminal evidence to each adjacent pair, then run a fresh independent coverage audit. Retain the existing accepted bounded conclusion and resource result; do not rewrite prior verdicts.

## Immutable input/report hashes

The corrected TSV is used instead of its malformed-header predecessor. SHA-256 identities bind the reports used here.

| Input | SHA-256 |
|---|---|
| `185-188-method-hash-sweep/report.md` | `d07773bc740feebde32a76f035556c23ab5496bab27e7e479d306fe85c96a7fb` |
| `185-188-method-hash-sweep/movement-core-sha256.corrected.tsv` | `54f735feddba839f19527ae7b9bee36d2f9000e5dda42cb7b36fe7b1c96814e6` |
| `185-188-method-hash-sweep-audit/audit.md` | `336ed48262297ad046847c8d5a84d7fd234083461786943c81a3da6b19da84aa` |
| `185-188-method-hash-sweep/correction-note-v2.md` | `cb2c1084d9742ec75f403cd27966d59504fa759b1433bbea0630952068377d63` |
| `188-189-movement-bridge/boundary-audit-1.8.8--1.8.9.md` | `06247fa2ec619c98c56b8d774a6340ed839edf7213ac35e1b9e013e2b221a97a` |
| `188-189-movement-bridge/changed-source-hashes.csv` | `46f89d86ca6d2b4a4d6e41e30b4e53f905550328bc553d33425ce04a8bef90a2` |
| `188-189-movement-bridge-audit/audit.md` | `19023d9ab5d805561b9e66640a48778c0c9a3a36661ebf23b8c127eb23363598` |
| `188-189-ordinary-movement/report.md` | `f2357c93a2e327a62f14d20e1fa68e1891e8efdad15b25bbb1c46ccb4b1fe86d` |
| `188-189-ordinary-movement-audit/audit.md` | `edba68edfeba36c16850957617a73bf9945b704df88f505b338f842697bd7de6` |
| `185-189-group-closure/closure.md` | `8e1d55b826a0221c9a0d53942334dac693e8d4b9ae158e5531fb75463e16f257` |
| `185-189-group-closure/resource-correction.md` | `e1c11425af54a812ac2abdc426dae78f1e5ca6ae6af3ce8b15b2039de4215f1f` |
| `185-189-group-closure/resource-registration-supplement.md` | `5533875a8ebe54a180d9b45d7b5cdfb11895c55e1f53e886c24decf628b96493` |
| `worker-185-189-resource-bootstrap/audit.md` | `281d8ef34b9da7925991b0226230fc7e56e0d021f7fbd4d2591f0b1134846746` |
| Recovered `1.8.9.json` | `341af5f47a6b5bc137bfe681f4f3533e081041290aabbf9d7ebae9881de768f` |
| Recovered `1.8.9.jar` | `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298` |
