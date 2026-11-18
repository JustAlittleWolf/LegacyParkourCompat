# Frozen resource roster: 1.15.2 to 1.16.1

Frozen: 2026-11-18
Pair: Minecraft 1.15.2 -> 1.16.1
Question scope: two independent builtin fluid-tag dependencies only. External packs are excluded.

## Roster

1. Builtin `minecraft:lava` fluid tag in each release: retain the raw resource document, including literal values and any include directives; resolve the effective fluid members, distinguishing source and flowing fluid IDs.
2. Builtin `minecraft:water` fluid tag in each release: retain the raw resource document, including literal values and any include directives; resolve the effective fluid members, distinguishing source and flowing fluid IDs.

The four release/tag cells are frozen as the comparison set. A member list or raw include list is not frozen because exact resources were unavailable in the permitted local evidence. Do not substitute remembered data or an external pack.

## Exact evidence identity available

- `decompiled_minecraft/1.15.2/mojmap.ready.json`: file SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; records release `1.15.2`, mapping `mojmap`, client archive SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`, and artifact-manifest SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`.
- `decompiled_minecraft/1.16.1/mojmap.ready.json`: file SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`; records release `1.16.1`, mapping `mojmap`, client archive SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`, and artifact-manifest SHA-256 `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`.

Corresponding `mojmap.provenance.json` SHA-256 values are `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` (1.15.2) and `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98` (1.16.1). They name the pinned artifact location `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\artifacts`, which is absent. The ready records pin client and artifact-manifest digests, but those digests alone do not reveal tag resource contents.

SHA-256 is over UTF-8 bytes of the text before this terminal line, including its preceding LF.
SHA-256: 30d8c96d402a6003524c582d38de01c8f20da3d14e16aa780ff0e99c61f98c4b
