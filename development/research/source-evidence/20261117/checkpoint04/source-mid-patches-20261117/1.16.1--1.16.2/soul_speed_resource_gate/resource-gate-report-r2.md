# 1.16.1→1.16.2 Soul Speed tag resource and state witness gate — r2

Status: the pinned package membership and static ordinary-state witness are now source-confirmed for both official client releases. This closes only the tag-resource/applicability gate for this bounded candidate; it does not complete pair coverage, all dependencies, implementation applicability, or runtime parity.

## Source and artifact identity

Canonical source identity is Mojmap, using exact source roots and verified ready records in `build/source-inputs-115-116-20261117/README.md`.

- 1.16.1 source manifest SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; provenance SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; mapping artifact SHA-256 `6cc374f819bca44d940f8912044b07bdac3e240a02e37117ae1d4ad63c21409b`.
- 1.16.2 source manifest SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; provenance SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; mapping artifact SHA-256 `c95a7046ab43371cc476b8b51978c6f2155c62f4cc5756b05c8ef81e0f11691f`.

Official Mojang version metadata resolved the exact client download URLs (full URLs and hash bindings are recorded in `input-reference.json`). The jars were downloaded from `piston-data.mojang.com`; verified jar SHA-1 matched the immutable object-id path and SHA-256 matched the pinned `client.jar` value in each endpoint's `artifacts.sha256` and ready marker:

- 1.16.1: client jar SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`.
- 1.16.2: client jar SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.

Only the exact allowlisted `data/minecraft/tags/blocks/soul_speed_blocks.json` archive entry was read from each verified jar. Each entry is 94 bytes with SHA-256 `c86fbd7bcfa2b94c881f0ba9980a694b28150a5b0b628aec0cfedc552f68994f`. Both payloads have `replace: false` and values `minecraft:soul_sand` and `minecraft:soul_soil`. The extracted payload files are under this packet's `1.16.1/` and `1.16.2/` subdirectories. The temporary client jars were removed after verification; no other archive entries were extracted or read.

The earlier source-only packet is preserved at `resource-gate-report.md` (SHA-256 `416aeed493284361ca105189cf47ba2b301fe6d0d858fdf924b563a01b55e935`); its former local-artifact-missing limitation is superseded by this r2.

## Source-confirmed movement route and state witness

`net/minecraft/tags/BlockTags.java` binds `SOUL_SPEED_BLOCKS` using key `soul_speed_blocks` in both versions. `net/minecraft/data/tags/BlockTagsProvider.java` declares the default entries Soul Sand and Soul Soil in both versions at line 515. Source hashes are recorded in r1 and were confirmed against each source manifest.

At `LivingEntity.onSoulSpeedBlock()`, 1.16.1 reads `getBlockStateOn()` and 1.16.2 reads `getBlockPosBelowThatAffectsMyMovement()`. Both `LivingEntity.getBlockSpeedFactor()` implementations return 1.0F for a positive Soul Speed enchantment level and a matching tag. The client-side movement consumer calls the block speed factor while applying movement; exact source methods and file hashes are in the already independently audited candidate `soul-speed-block-position.md`.

Static ordinary-state witness: put Soul Sand at block y=63, leave y=64 air, and consider a standing player during a jump with position/box minY=64.3 centered over that column. Old `getOnPos()` floors `position.y - 0.2F` to y=64. Since that state is air and Soul Sand is not a fence, wall, or fence gate, its fallback leaves the selected position at y=64. New `getBlockPosBelowThatAffectsMyMovement()` floors `box.minY - 0.5000001` to y=63. The 0.425-block clearance over Soul Sand's top y=63.875 is a collision-free portion of an ordinary jump arc; therefore the two exact official default tag payloads make the new query match Soul Sand while the old query sees air. With a positive Soul Speed level, the source-gated horizontal movement factor can consequently differ. This establishes a static vanilla legal setup, not that any generated map contains it. Arbitrary datapack tag overrides are outside this packet.

## Scope limits

No source tree was modified. Extraction was restricted to the one allowlisted JSON entry per pinned official client jar. No build, test, runtime or pair-freeze claim is made. This gate can support candidate applicability review, but the exact pair still requires complete source inventory/dependency closure and independent pair coverage audit.
