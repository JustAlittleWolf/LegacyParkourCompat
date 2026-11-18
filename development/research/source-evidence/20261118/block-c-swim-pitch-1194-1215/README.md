# Swim-pitch vertical adjustment: 1.19.4–1.21.5 exact source evidence

This archive preserves thirteen exact, independently accepted adjacent-version comparisons for the ordinary local player's direct swim-pitch vertical-velocity writer across 1.19.4–1.21.5. Coverage is specific to this operation, not full movement-pair coverage.

The scope is the voluntary ordinary active nonpassenger `Player` writer, its exact pitch/view/LUT math, gates, and statement order. The records do not establish runtime parity or behavior beyond each compared pair. Slow Falling and other effects are separate operations.

| Pair | Source report SHA-256 | Independent verdict SHA-256 | Supplemental binding |
| --- | --- | --- | --- |
| 1.19.4→1.20 | `8e39737fe6085dfff7924ac6fe6b08798829553ad754d1db44c6317b16497e79` | `461feb2a83987f3ced91bd9a941a5241ca5f192ac6870c05a3ce28e91642d40e` | — |
| 1.20→1.20.1 | `caee8e79a79bb65222e82645928ca7d0d27425826215dba554ac6c855e2d6cae` | `ccf6f7a21028d147552333b101094350980e2e3ba708fc4e32c5d82c45c58fe0` | — |
| 1.20.1→1.20.2 | `e1fa9633a4d79b2fe635a262e35f0e63cfa65de920e7ec9600409b4c1174f788` | `57506c061553abc7d7a342586f20831a4997b4c6c67feeb4f0073520469380ff` | — |
| 1.20.2→1.20.3 | `00e6b12626931848abba007ad5da169f1e18857c7174fe6524bd8c050f822879` | `716069f9ac4c5342eac5a50a72f69dfe467ce82193352a3f1d8f2fdab2888e27` | — |
| 1.20.3→1.20.4 | `b2e481fa99996d2c7c560f0c64fca460d297b491f443e6e9ed5be9b1547926bc` | `64d8494097ce6123274639e6866e1a2adae2d981fea62e56dc15eea7d69c3829` | — |
| 1.20.4→1.20.5 | `ba774fcf28e5f8bc50b88e72124dade3eabdb5165190dd31f13c9b8ea7447758` | `9549961163ac3c70e1a0639368b68278869de4f7221782ae52a93f75d4f88e71` | — |
| 1.20.5→1.20.6 | `b4a8bc5e4dc3978ef2580117dcaabf622279664c1f9507f8b7553ee44c3a703a` | `c2e3ced2e365674d8069373ebcd269467364a9eaa509edf841de795af1ce2548` | Strict two-phase blind audit; earlier limited audit excluded |
| 1.20.6→1.21 | `b4b9d3cf0678e7624c97625739dbb3043f050121b18db9b86c83f6fed39547ff` | `441826639a89312ba18d90b93f384854efac8469cacb4712d4807e499ddbb2a1` | — |
| 1.21→1.21.1 | `c0d2c03d515de8461d9c4a1e411d4965a55efdfd727dc1512c5ec5b1fde48c64` | `094c091486e86abfc77edf468947d52a1626bd00460a1c3be22d1f7baf5f6830` | Signed binding `52ff07c5267719b6997067c38e20f31510937461b3b3bd9fe473e376a3d3dc39` verifies unchanged originals |
| 1.21.1→1.21.2 | `63e508f3ee17a6ee981b7ebd9db2d828d04d19059c256632187f12809adfed4f` | `bb6158fae00cec9d78dabaa47f3d2e3b5d682e40be57d68f411131fe2041c20c` | — |
| 1.21.2→1.21.3 | `727a0d34eed04539298d7c944832bf3a54928fac6e00cad075aab94b4797d024` | `d46bcfc1cf14e3f512074f789bceb46e3cb031104518fc7547245f71740055f5` | Source identity `47e22434063082244fc48019f77cfb810876313fedf242712fc6e8d858a8f05e` resolves canonical source readiness; raw cache absent |
| 1.21.3→1.21.4 | `715ebe272bc2f5502c162700352289d42ecf3f691ff59bf99c6b40d785ea7351` | `68a24bab3b375d63a1bbc64de571a98c2e3d42ae9c31d466beae59c68c0d6fa7` | — |
| 1.21.4→1.21.5 | `d7fa78d6e0667f40b51d0a8dc5e94f75c2b8149dc0202b09796831034aa18273` | `6b717899fd31c50d283c07cb09cb85fb7e679098e78e878170cd6b390b56a1e5` | — |

The 1.20→1.20.1 edge uses a clean two-phase blind audit; its initial reviewer is excluded. The 1.20.1→1.20.2 verdict corrects full-file identity while confirming the audited method and LUT slices are equal. The 1.20.2→1.20.3 verdict accepts embedded provenance. The 1.21.1→1.21.2 verdict keeps raw-vs-modified pitch semantics separate from the ordinary clamped input path; it does not generalize out-of-range inputs. The 1.21.4→1.21.5 audit separates parent-wrapper removal from the reachable ordinary local fluid movement path.

The 1.21→1.21.1 verdict is accompanied by a signed binding sidecar for the unchanged original report and verdict. The 1.21.2→1.21.3 source identity resolves canonical version-root readiness; raw cache absence remains. This archive is based on `aae9c86464638cb5ed7faa535c92569b89484906`; it makes no product or backlog claim.