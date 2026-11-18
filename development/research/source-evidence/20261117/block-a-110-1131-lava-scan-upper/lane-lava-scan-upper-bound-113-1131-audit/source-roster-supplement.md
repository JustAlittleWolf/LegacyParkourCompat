# Frozen supplemental source roster — movement and scan dependencies

Frozen before opening these source bodies. Added solely to close the assigned bounded operation's named dependencies: local-player tick/chunk gate; held-jump and travel dispatch; contraction and ceiling arithmetic; material predicate; default loaded-chunk gate; chunk-source/client missing-entry dispatch. This supplements `source-roster.md`; it does not expand the assignment to full-pair coverage.

| Relative path | 1.13 SHA-256 | 1.13.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` |
| `net/minecraft/util/math/Box.java` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` | `0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d` |
| `net/minecraft/util/math/MathHelper.java` | `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| `net/minecraft/block/state/predicate/MaterialPredicate.java` | `1a8a9129667637ef5ccb217ad2b55fb2117653bedd19d2e62dbda04f285f1c54` | `1a8a9129667637ef5ccb217ad2b55fb2117653bedd19d2e62dbda04f285f1c54` |
| `net/minecraft/world/WorldView.java` | `b0d1406fb04eacbc644a78548f858d5ff6c14ad781927832cb374e985f32a543` | `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f` |
| `net/minecraft/world/chunk/ChunkSource.java` | `5aa39f809e3696212a80c1601f48cce63cda5edac120f89b1df42e526796741e` | `88cdc302ab22957707cb721425092f64745b18320556b77adef1a5a92fef3d5b` |

Each SHA-256 was recomputed and matched the version-root source manifest.
Client-cache missing-entry result dependency (frozen before opening its body): `net/minecraft/world/chunk/EmptyChunk.java` — 1.13 SHA-256 `42ef83dd4325eaa9589f243e0034e86cd76cf357fb33ee82c515d043e336c2a1`; 1.13.1 SHA-256 `42ef83dd4325eaa9589f243e0034e86cd76cf357fb33ee82c515d043e336c2a1`. Both recomputed hashes match the source manifests.
