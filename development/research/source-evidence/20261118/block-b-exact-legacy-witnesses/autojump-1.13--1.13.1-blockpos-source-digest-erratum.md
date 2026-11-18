# Erratum: BlockPos source digest

Bound immutable witness: `1.13--1.13.1-autojump-collision-enumeration.md`, SHA-256 `62b424ae55069feecd84f6119ba3c1c18832ee0865a2fb1b2dd4c298667588eb`.

Correction: the witness's printed `BlockPos.java` digest contains a transcription error. Direct SHA-256 rechecks of `decompiled_minecraft/{1.13,1.13.1,1.13.2}/ornithe-feather/net/minecraft/util/math/BlockPos.java` all yield `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd`. The exact source-manifest digests remain 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`, 1.13.1 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228269c932d3abba`, and 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Verdict consequence: none. This is a digest transcription correction only; the identical exact `BlockPos.java` source still floors the same Vec3d center to the same cells. The legal geometry, source-ordered reads, predicted cooldown divergence, and static-only scope are unchanged. The bound witness remains COMPLETED.