# Glide lower gates and packaged modifier data (2026-11-18)

This receipt archives three exact source/audit pairs. Frozen inputs are copied byte-for-byte; the independent verdict controls corrections and limits for each report. These source findings do not establish product reachability or runtime parity.

| Pair | Report SHA-256 | Audit verdict SHA-256 | Bounded disposition |
|---|---|---|---|
| 1.17.1→1.18 Elytra lower gate at climbable feet | `7d0db2cef6a8973477996de2999785421a5329e5dcce236e412225196845e85a` | `75405bdfcbb049131c0f18d2d979d7ecaf921379cb9fa58d386cef48e07602de` | Accepted unchanged for the bounded already-active dry glide operation: strict vertical `> -0.5` pre-move write of `1.0F`; the ordinary climbable cleanup path is bypassed. Product reachability remains a separate hold. |
| 1.18→1.18.1 Elytra lower gate at climbable feet | `0549a8d8d7b3df67423796f645ea826ea70f24bb2ea58da097c66bea90c28cc9` | `f566b0573966180bf411be73fd6bc7557db1595e05ab9077bd1bbcbc2c0587e2` | Bounded no-difference result accepted. The verdict corrects the report's provenance conclusion and `LocalPlayer.java` hash typo; raw cached artifacts are unavailable for independent rehashing, while adjacent manifests and run records support exact source provenance. Product reachability remains separate. |
| 1.21.6→1.21.7 packaged movement modifiers | `e53dbac9f90967a87743ce0e61db2db916191b12fae8863877ba3dcbd3bc7be5` | `d36cb8a8b552add66d5ed49a1ef1099b67b1bdb4c2ca0a02f084cd5d89e0a9fd` | Accepted for the exact inspected entries: packaged payload bytes are identical, with the two cached client JAR inputs independently verified. Adjacent .5/.8/.10 cached client JARs were absent in the bounded checks; no generalization to those releases or all modifier data is made. |

The separately provided 1.18.1→1.18.2 candidate remains held: its current report bytes do not match the SHA claimed by the verdict. No disposition is published until an independent binding correction arrives. This receipt does not resolve implementation reachability or alter the product or central backlog.

The three report/verdict pairs above are frozen byte-for-byte copies. Static whitespace checking uses Git's `cr-at-eol` and `-blank-at-eof` exceptions for these verbatim artifacts where their original CRLF, Markdown hard-break spaces, other trailing-space, or final blank-line bytes require it; the README uses LF without trailing whitespace.
