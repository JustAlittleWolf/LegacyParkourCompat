# Authoritative local mouse-turn pitch evidence: 1.20.5–26.2

This archive preserves five accepted source records for the ordinary local mouse-turn pitch setter: four adjacent comparisons from 1.20.5 through 1.21.2 and a separate 1.21.1→26.2 destination witness. The destination comparison does not replace the adjacent-edge evidence or establish behavior in unexamined profiles.

The bounded operation is the first authoritative pitch setter reached by a local ordinary `Entity.turn` mouse input path, including the accepted float arithmetic and setter effect. Raw global pitch setters, teleport/server dispatch, rendering, network state, runtime parity, and unrelated callers are outside this archive.

| Record | Source report SHA-256 | Independent verdict SHA-256 | Bound correction, identity, or acceptance SHA-256 |
| --- | --- | --- | --- |
| 1.20.5→1.20.6 | `104fd7bf66c9ed9247ceb38a5337b07ac3b578a4cef268ffec5217f2bc39ecde` | `79015323fcffb8da794453c8d1c69de972f577cd0007a70f97542ce2e84ff0dc` | — |
| 1.20.6→1.21 | `331d0c572e31ae77461f03119179db70c3299031d0f498120361fe34c66da21d` | `ccff33799a6fa109a5a886b734c47dafdd1aa2f3ec7be0315bf1c9f1a23f2264` | Source identity `99afb5171ab5488df54c9053edb3afc12b10cf8045b039d7bcf0f4ecf32bf575`; phase-two addendum `da5fd2a563eee5cf20a4687d43646df32f7e95c1b730f4f4e28103a52b7b283a` |
| 1.21→1.21.1 | `2b7b95e3446c85a8b4bfd69e79a38ba9dbbad2bade7224fb848a63f7a9d8a3ee` | `2d02babe53b99883b92a29ccba54fa68e07e22de9654c9589bc4e4e58a3667af` | — |
| 1.21.1→1.21.2 | `fee374a8b55ec931727265edad321cf8cd4e6c67f8d31b7c3f1f79ae9dc9d97e` | `245811badbe89fbf2dbe2f47cc3270b1b052b95415de1b76b96fcdbbb800b7ea` | Source correction `0cc1e0142dbd74dac3339cf97ad4626d8ece0a8ebacfb113eb166badab1fc134`; audit acceptance `9445b0e8ed422d9d96997e5a4699ff3c887ec97a80b3fdb60ced7fdaa9ba8308` |
| 1.21.1→26.2 destination witness | `1cd80e3e5d24fbcd84988dd470b7ee22b619e9579a112240cb4e0b4db770c9ab` | `aa73b09290c5abc024b5cee9685bc66cb0ffb78dc677578c44ac62dd8f07334b` | Source correction `00f51c04b2320f389b2335da2e1c2e0e18dc397209c859eaa3a1f2494e153a45`; audit acceptance `e51715b3e48947441d9cb78c17ae6d55af2e78dc7039ab70d88ec8995779304f` |

The 1.20.5→1.20.6 audit rejects the author's double-to-infinity example and instead accepts its own finite source witness, approximately `450.00003F→90` before glide movement. The 1.21.1→1.26.2 witness records the accepted default float constants and exact `90` versus `0.0001220703125` result; the maximum-sensitivity witness remains source-derived, not a general trajectory. The corrected `/tp` route has distinct server dispatch and is excluded from the mouse-only scope.

The 1.20.6→1.21 phase-two addendum and 1.21.1→1.21.2 correction/acceptance files preserve their exact bindings. This archive is based on `aae9c86464638cb5ed7faa535c92569b89484906`; no product or backlog claim is made.