# Swim-pitch vertical adjustment: 1.17.1–1.19.4 exact source evidence

This archive preserves eight exact, independently accepted adjacent-version comparisons for the local player's swimming pitch-to-vertical-velocity operation across 1.17.1–1.19.4. This is operation-scoped evidence, not full movement-pair coverage.

The bounded claim is the direct swim-pitch vertical adjustment, its view-vector/LUT math, gates, and operation order. These records do not establish runtime parity or a trajectory. The 1.18.2→1.19 audit separately notes downstream `Entity.move` support-state behavior; that is outside the direct writer claim here.

| Pair | Source report SHA-256 | Independent verdict SHA-256 | Supplemental source SHA-256 |
| --- | --- | --- | --- |
| 1.17.1→1.18 | `50ba29ac29e6f36467fbb1c22b565d85375dc5300ecbc692b558ea66544e52a1` | `ba1b0ac4e0325dd485f388621a510197634227209d66cd9811e1b2192ede3d59` | — |
| 1.18→1.18.1 | `b95b8a20749c96014df37998293ec741a0f48ed183e9642834cd46604c010368` | `af3f6502f750ac24d4eb5544a50e1b77a513530e23cef7bddd733fe209026335` | — |
| 1.18.1→1.18.2 | `579dc58bd68693a2a91f5f8ddeb80dff7500ed5c04d500df934b7010bc47dc7e` | `47c56c8ca95872fd42f9c1ae19c198f60e7467565d64c3588717bf56287131f0` | — |
| 1.18.2→1.19 | `ad442450b63d087797287028cd8133fd4886eeb3a2afe4b37b7ab0bc1887d825` | `2ab9082160122e57a17a4124375ee6931b45628752bf51e2848308102d8a3d67` | — |
| 1.19→1.19.1 | `ac1f0f43914841bd8be9fee86bb122cc07c5f4677ea1e12591971985ee515208` | `e4db120a885c954f7c1d37d3c1c0fd91727c425587a9a343aa497a638f4a036f` | — |
| 1.19.1→1.19.2 | `d04f0879d3ffeab30c39a700eb31fac811535f8690e2d526baf0519c3779e261` | `e2bc16d2b574c9313c33503a0c26c38a0d93916f0a0b07b0fb571a85da7ed1be` | — |
| 1.19.2→1.19.3 | `384786241a2473c17372aeec0b112147e1626a4e23ed08d540dc5583ed65ae04` | `78750f808638dcd03ce3b87a749eac4e8c3d88c90a353756f6aecc4b7c9de7f4` | — |
| 1.19.3→1.19.4 | `8003852493e5879fa67270b8b60202f06d189ef88af12442ffee556cdf4e7a38` | `07fd09a4f606e5edbf7c25aa472229a27f76852b8e7f6219b4f3d500d6e87a59` | `addendum.md` `ad79886295a9395abffe891167a10e5e4a5698e9bc71c49af709cee31c4c18bc` binds the exact `BlockPos` predicate equivalence and operation order |

The final edge's audit confirms the ordinary inherited `LocalPlayer` gate is reachable for the scoped writer and distinguishes the equivalent `BlockPos.containing`/`Mth.floor` coordinate conversion. No claim extends to other operations or runtime parity. This archive is based on `aae9c86464638cb5ed7faa535c92569b89484906`; it makes no product or backlog claim.