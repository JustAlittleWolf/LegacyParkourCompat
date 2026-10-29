# Correction supplement: block-entity flag and cube classification

This immutable supplement corrects the full-cube classification sentence in the finding. It binds:
- Finding: FINDING.md SHA-256 42DA4BD7258AD6AA9653A4AC8A1DE13A6C7451C177780AFC95F324885F1475A8
- Independent review: dda-blockentity-audio-family-review/review.md SHA-256 932B1C26523F18A97D81FA1093942ACECC08BF10B6F5F6194A0EA16E87F928FC

Exact source: 1.8.9 Ornithe Feather net/minecraft/block/Block.java. At lines 245-247, Block.isCube() returns true unconditionally. Lines 668-673 are hasSilkTouchDrops(), whose predicate is isCube() && !hasBlockEntity; this affects silk-touch-drop behavior and is not a cube/full-cube classification. BlockWithBlockEntity.java:13-20 sets hasBlockEntity=true, but that flag does not change isCube().

Withdraw the finding's statement that Block.isFullCube() becomes false through the block-entity flag. Correct statement: these blocks remain cubes by Block.isCube(); the block-entity flag makes hasSilkTouchDrops() false. The independent unit collision-box and inherited ray-box conclusions are unchanged.

The original finding and REVISE review are preserved unchanged. This supplement addresses only the identified source characterization.
