# Independent source audit: climbing predicate r4

Verdict: **ACCEPT**, bounded to the stated endpoint predicate difference only.

Candidate: `build/later-source-20261116/1.20.6--1.21.11/climbing_movement_revision_r4/coverage-r4.md`  
Candidate SHA-256: `D3DF5CB8D819484416F4B05E772BEA10D44755373AFB9A83251882E511D77B6E`

## Identity and source integrity

The exact endpoint ready markers identify 1.20.6 Mojmap and 1.21.11 Mojmap. The ready-marker, source-manifest, artifact-manifest and provenance-file hashes recorded by the candidate match the files in `decompiled_minecraft/`. Ready markers pin source manifest SHA-256 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` and artifact manifest SHA-256 `e5882622bcf22b3e3c2c96c73843a1308e11e3096f4feefd945ea5808933ce31` for 1.20.6, and source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` and artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` for 1.21.11. Ready marker hashes are `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` and `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; provenance hashes are `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` and `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`, respectively.

All four cited source files match their exact `mojmap.sources.sha256` entries and local bytes:

- 1.20.6 `net/minecraft/world/entity/player/Player.java`: `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2`.
- 1.20.6 `net/minecraft/world/entity/LivingEntity.java`: `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2`.
- 1.21.11 `net/minecraft/world/entity/player/Player.java`: `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81`.
- 1.21.11 `net/minecraft/world/entity/LivingEntity.java`: `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`.

## Claim audit

The old 1.20.6 `Player` class has no `onClimbable()` override. Its inherited `LivingEntity.onClimbable()` first rejects spectators, then returns true for a state in `BlockTags.CLIMBABLE` or an open trapdoor whose block below is a ladder with matching facing (source lines 1541–1568).

In 1.21.11, `Player.onClimbable()` returns false when `abilities.flying` is true and otherwise delegates to `super.onClimbable()` (lines 2043–2045). The 1.21.11 inherited `LivingEntity` predicate retains the open-trapdoor/matching-facing-ladder fallback, with a distinct fall-flying/tag gate earlier in that method (lines 1669–1696). Therefore, for a non-spectator player with flying ability enabled whose old inherited predicate would be true, the two endpoint `Player.onClimbable()` results are true in 1.20.6 and false in 1.21.11. The candidate states this as a source-predicate fact and does not claim a movement, position, velocity, or fall-distance consequence.

The candidate appropriately withdraws the prior ineffective movement witness: it notes that the flying LocalPlayer suppressing-sliding helper is false and the examined shift request is exactly the `-0.15F` clamp threshold. The predicate delta is accepted as bounded source evidence only. The report does not establish a reachable movement outcome, an exact introduction release, or implementation applicability.

## Limits

This audit accepts only the immutable predicate claim above. The 1.20.6→1.21.11 pair is not closed; intermediate releases, other travel/collision routes, and runtime parity remain open. No product or implementation conclusion is made.