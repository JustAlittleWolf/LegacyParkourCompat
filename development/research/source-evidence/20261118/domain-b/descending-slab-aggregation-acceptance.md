# Descending primary slab collision in 1.13 through 1.13.2

The older exact source uses a signed `1.0E-7` start-box shift and `ONLY_FIRST` for the primary swept collision query. Negative Y shifts the start exclusion up (`WorldView.java:191–196` in 1.13 and `:158–163` in 1.13.1/.2, Feather Gen2 build.2). The dry bottom slabs at the next two X cells are outside the Y clip's transverse X interval. Y therefore remains unchanged and the airborne step-up predicate stays false (`Entity.java:578–602`). Once X is clipped, the 1.13 aggregate retains the exact positive gap `4.9999999918171056E-8`. The ordered 1.13.1/.2 stream returns zero before applying the second slab (`VoxelShapes.java:194–205`).

The initial claim misidentified the old literal `-0.003` assignment as dry travel. That assignment belongs to the water branch (1.13 `LivingEntity.java:1467,1598–1630`; 1.13.1/.2 `:1486,1617–1649`). The separate old signed-short motion packet path supplies exactly `(2.0,-0.003,0.0)`, and the strict velocity cleanup retains Y at equality. The audited 26.2 `LpVec3` input is distinct: scale 2 and Y code 16358 yield binary64 bits `BF69006401900600` using the decoder's sequential arithmetic. The close direct division has different bits. Dry no-input `SELF` movement consumes the observed vector intact. The bounded operation equivalence uses that exact modern observable input and the historical collision geometry. It establishes no packet quantization parity.

The product guards the actual unadjusted airborne player box and swept request, preserves the same cell rows and strict orthogonal slab overlap for its float standing dimensions, and verifies the two dry Smooth Stone bottom slabs through both historical state and native contact provenance. The observed vertical component is large enough that moving the resolved position retains the older tiny horizontal offset. Runtime parity remains unverified.

| Evidence | SHA-256 |
| --- | --- |
| Original negative-Y geometry source | 5687321c7b074c867d555c2537697ba0fb51a01422c5656a4ecbf279db1a203af |
| Original geometry audit | 377971092ff6846355b8aab78a06d0c46b36211188597f1356673274f181905d |
| Corrected dry producer audit | 210a88579c0b7d0575ec570cfb1ae05887ac7ff9ee6afaf6e8a9f1da22219c2f |
| Modern observed movement source | d8df3b1993f27f49435b1cfcf5b9e2eceb14241628df9ad8eec150ad35adcfae |
| Modern observed movement audit | f4e83438eba2d019d97e15f620dd32535ffa4b4666fea919b9fee1c4bd9b7865 |
