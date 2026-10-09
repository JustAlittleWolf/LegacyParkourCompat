# F-008 boundary memo provenance check

- Review date: 2026-10-09.
- Scope: immutable identity and source-artifact provenance only. This record does not repeat the accepted movement-source analysis.
- Existing boundary decision: **ACCEPT**, preserved as recorded in commit `c578b68bbbef1442fd23367bfef5fe6dc9e39f89`.
- Accepted memo: commit `1bfc993c0602c00b22622d651c93fe640d4a4ac1`, path `workflows/source-boundary-reviews/2026-10-09-F008-border-edge-gate-boundary.md`, Git blob `864c15ea896e688c339d31b054963a6d6ea76b9a`, raw SHA-256 `25cc8c489b5ddea46b07abae01db1bc5d444d28d38f7f075ad0631cc0f840040`.
- Existing independent acceptance record: commit `c578b68bbbef1442fd23367bfef5fe6dc9e39f89`, path `workflows/source-boundary-reviews/2026-10-09-F008-border-edge-gate-review.md`, Git blob `da3964771aae9c05b2f979f4887b16d0ba489f78`, raw SHA-256 `6e4193cb85a66fd4ea289f79f34f0a2edf058423ef6ca5c31daffbc48ac49259`.
- Accepted finding snapshot: commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, path `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-008-border-edge-gate.md`, Git blob `58ebc5b2dbff5f0e409fdceb38b0466e89322ce4`, raw SHA-256 `3a8a5258cb741497a52085f76688df7ca32161dcbd0e068ffe5ee70b3a271afd`.
- Earlier independent finding review: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, path `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, Git blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`, raw SHA-256 `ee96f6564b366b9622bfed23bb31331018613d64589ee1215d842a2165307d3e`.

## Verification

The memo path resolves to the stated Git blob at its memo commit, and its raw bytes hash to the stated SHA-256. The existing acceptance record likewise resolves to the stated blob and raw hash; it names that exact memo commit, path, blob and raw SHA-256 and records **ACCEPT**. The accepted finding and prior finding-review paths resolve to their stated blobs, and their raw hashes match the recorded values above. No replacement or mutation of any accepted bytes was found in these bindings.

For the four exact Mojmap source publications named by the memo, each `mojmap.ready.json` reports `ready` and the matching version/mapping identity. The source-manifest and artifact-manifest SHA-256 values in the markers equal the memo's recorded values, and recomputing the hashes of both manifest files matches each marker. The sampled `Player.java`, `CollisionGetter.java`, and `WorldBorder.java` file hashes for 1.17.1, 1.18, 1.18.1 and 1.18.2 match the memo's source table. The identities checked were:

| Release | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` |

## Disposition and limits

No immutable-identity or source-publication mismatch was found. Preserve the existing **ACCEPT** for the exact boundary memo snapshot. This provenance check does not expand that acceptance to other releases, player outcomes after the normal movement solver, border damage or warnings, command behavior as a movement feature, non-player collision, Java implementation, or runtime parity.

This is a provenance-only record and does not provide a new blind source-physics verdict. During initial intake, the boundary memo's resolver/disposition section was read; this record excludes that section from its evidence and conclusions. No implementation Java, wiki material, tests, build, or runtime was inspected or run.
