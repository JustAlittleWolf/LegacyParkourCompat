# Erratum: scan-cap wording and AutoJump headroom origin

This erratum supplements, and does not alter, the earlier audit or its source evidence.

## Bound inputs

- Source proof: `domain-b-autojump/early-border-alias-source/evidence.md`, SHA-256 `a8143fc88eedd05955dd9b64a7803d77d4a83749800b4a9f133d2d138ac547b2`.
- Original audit: `domain-b-autojump/early-border-alias-audit/audit.md`, SHA-256 `817207b8266b51e046d5b0bbb4ffa2e5272825faafd7e6aebbb12c2e1d43676b8a`.
- Rechecked all seven exact local Feather Gen2 sources. Their `World.java` bytes match the source identities in the proof; for 1.10/1.10.1, each embedded provenance `sources` entry matches. For 1.10.2–1.12.2, each ready marker's source-manifest SHA matches the manifest bytes, and the `World.java` entry matches its current file.

## Corrected scan-cap wording

The horizontal side-cell Y predicates divide by exact patch, not a single “1.11 line” rule:

- **1.10, 1.10.1, 1.10.2, and 1.11:** `if (q <= 0 || r != k && r != l - 1)` omits both `k` and `l - 1` on a non-corner side column; corner columns are excluded by `q != 2`.
- **1.11.1 and 1.11.2:** the scan instead uses `if (!bl3 && !bl4 || q != l - 1)`. For side cells this omits only `l - 1`, retaining `k`.
- **1.12.2:** the same top-cap-only predicate appears: `if (!bl3 && !bl4 || q != l - 1)`.

This refines the earlier audit's side-cap summary. It does not change the bounded fixture verdict. At feet Y=64 and height 1.8, the AutoJump query box spans Y=64 through 65.8; the collision scan computes `k=floor(64)-1=63`, `l=ceil(65.8)+1=67`, and iterates Y values 63–66. The candidate at y=64 is neither cap (`k=63`, `l-1=66`) and is visited under all three predicates. With the previously verified loaded, non-corner east-face cell, it remains eligible for synthetic stone. The conclusion is therefore limited to this bounded query and does not imply identical cap behavior elsewhere.

## Headroom read origin

The first two AutoJump clearance reads are not based at the player's feet: `LocalClientPlayerEntity.autoJump` constructs `new BlockPos(this.x, this.getShape().maxY, this.z)`, tests that block's collision shape, and if empty advances with `blockPos.up()` for the second read (1.12.2 lines 884–889; corresponding AutoJump source is byte-identical for 1.10/1.10.1/1.10.2 and for 1.11/1.11.1/1.11.2). For the stated feet Y=64, height 1.8 fixture, `maxY=65.8` is floored to block Y=65, then the next read is Y=66. The later candidate-top read at `blockPos2.up(1)` is a separate operation. The supplied air-at-clearance condition must cover those actual positions; where that is the stipulated fixture input, the candidate-top/rise reasoning remains unchanged.

## Verdict

**Original bounded verdict stands:** accepted for the supplied conditional fixture; no source-alias or manifest-pin defect found. The original unresolved ordinary movement producer / reachability gate and runtime-parity limitation remain open.