# Direct mouse-turn pitch response: 1.16.2–1.19.4 source snapshot

This archive preserves all thirteen adjacent edge comparisons in the 1.16.2→1.19.4 interval for the first authoritative direct player pitch setter/final clamp response, plus its distinct reachable first active Elytra movement use. This completes the operation-specific source sequence; it does not freeze all movement behavior in those releases.

The scope is the direct ordinary player response to mouse-turn input and the cited actual active-Elytra consumer. UI, mouse scheduling, renderer interpolation, network/teleport routes, and raw global pitch setters are excluded. These records do not establish complete movement-pair coverage or runtime parity.

| Pair | Source report SHA-256 | Independent verdict SHA-256 |
| --- | --- | --- |
| 1.16.2→1.16.3 | `4450a902b2fdd8dd699256f981361659b73af20dfe393ebaa62913f3af6d782d` | `72f121562475338fe89120f2399da5b40152f3c560bce1999ce47e8aca1feb6c` |
| 1.16.3→1.16.4 | `142e32cf5dce476dbd84a3d7a35e23d7985f3fbfa38dda94b0642c181dcc5044` | `d1c4b41723df89e972675c98a3353ad5b5eb7426fb8714e36004f99962ff5af9` |
| 1.16.4→1.16.5 | `86e35b22b1b87b926aae5d8d2bad7c5886826db66ebb3ddaec5b9ab65550c571` | `9a5dd7ac4a8b8a593ccc0026a6f0b35d04ca54ada6cc8cf6b9985241addeea21` |
| 1.16.5→1.17 | `8ee8e6fa5f2d3afa352cc52b906aa3064965dbbe8610689321823affe42ee587` | `c8129fc5e1c5623b6278104d7c1ff796ac00628d4f95734c8993bcef133a170f` |
| 1.17→1.17.1 | `0bcf6487bca05941d784bcfece7dcc2746adfdea6e9df8094ce5751ba3dbad77` | `dfa8262fd1b850be6e43011b86ab8a1afb9f7054e3824e871dd915fb789fc7f2` |
| 1.17.1→1.18 | `1ad1213578fd086fa7b199e11596ce1773667242714f383e8005efd44066764b` | `c06debd4ffd253c957b58fe5a7676f762c12822c703b75bb9afb39d1db43fa03` |
| 1.18→1.18.1 | `7c5ddca016665cdf72132e3767a8c431c1bc1e0f8a1b31b80afac4c474825c8f` | `7eacb9563e555cda16f2f2eaf362eb7539b880d1cd3a28729bc9b8803ba80c18` |
| 1.18.1→1.18.2 | `b97280980b087c13bb4d03c900f5cac6851a750fbd071a66347072de534d01bd` | `c805d24b5bafb52680e8d5b75b8157446185f4276058200b7b452fa738e17f87` |
| 1.18.2→1.19 | `be6b987dc68b832e6e8fd309dd1542bf37d77588400bf88bd7cc2feb5cf58350` | `07c15f0f7b6ca9d6d4e5e45bb39f6a5f83821a445d39025bcd0005ba5e703ba6` |
| 1.19→1.19.1 | `be35361679d2e9669a072d32ee84e37c5ec5de55eb96e95d6ca9bacedaa171ea` | `e772f80e9d19fefaee914c239170d259717950578ad7fb4b0536347cd4e415d5` |
| 1.19.1→1.19.2 | `264194e83a2e984f164df53bd19c9fb4fa57869ef8473ad5eb557474b68feb9b` | `833e213363f7f4b9fc15f3129c5762713289204e516627d4942d14fbd26b4524` |
| 1.19.2→1.19.3 | `0bd49ecbeeb3b187a6c3a0a97f5f7a903f12507d6bc735f5deda3410c56a3c81` | `3a648063522ce90a1d3372ef4abc6102283bc0794fc69df1f54cbe7fcad0618b` |
| 1.19.3→1.19.4 | `b5825d7088a32f78528f667ec98fc59fe8fda51fbf503e1e2206dc5fda9b7382` | `049a91799316cf3506895f64423e92ab7fbf413124e7d338486d8b3284fb5ab5` |

The 1.16.2→1.16.3 audit independently verified local version, client, and mapping pins but did not rehash all 36 inputs claimed in the author report; that scope distinction does not affect the matching Java-operation verdict. The 1.16.3→1.16.4, 1.16.4→1.16.5, 1.17→1.17.1, and 1.18→1.18.1 verdicts are bounded to the direct setter/read slice and retain their exact source identity qualifications. The 1.16.5→1.17 verdict accepts a changed in-range pitch output for its finite witness (60.000003814697266F versus 60.00000762939453F); the saturation witness still clamps both outputs to 90.0F. Any changed movement consequence remains a separate acceptance question, and this archive does not claim runtime trajectory change. The 1.17.1→1.18 verdict includes a separate identity sidecar; its author and independent finite witnesses differ in magnitude, and the raw-cache limitation remains. The 1.18.1→1.18.2 verdict retains its raw-cache provenance caveat; Elytra cosine is a separate operation. Slow Falling is separate from the 1.19.2→1.19.3 pitch claim. That verdict's accepted finite witness is the auditor's >360-degree case, not the author's +150-degree case. The 1.19.3→1.19.4 verdict distinguishes the accepted post-multiply finite witness. `git diff --check` reports source-authored whitespace in these immutable bound files: `1.18.1-to-1.18.2/report.md`, `1.18.1-to-1.18.2/verdict.md`, `1.18.2-to-1.19/report.md`, `1.18.2-to-1.19/verdict.md`, `1.19-to-1.19.1/report.md`, `1.19-to-1.19.1/verdict.md`, `1.19.2-to-1.19.3/report.md`, `1.19.3-to-1.19.4/verdict.md`, `edges/1.16.2-to-1.16.3/report.md`, `edges/1.16.5-to-1.17/verdict.md`, `edges/1.18-to-1.18.1/verdict.md`, and `edges/1.19.1-to-1.19.2/verdict.md`. These notices are limited to original source/audit bytes; normalizing would invalidate their accepted SHA-256 bindings. All other owned text passes the check.

Base: `aae9c86464638cb5ed7faa535c92569b89484906`. No product or backlog claim is made.