# Eastward moving Glass plate in 1.8 through 1.8.9

Plain Glass is a unit collision box and is piston-movable in the checked Feather Gen2 and 26.2 sources. The legacy registry sites are 1.8.9 `Block.java:863` and 1.9 `Block.java:710`; 26.2 resolves plain Glass through `Blocks.java:648–660`. Earlier equivalent registrations are at 1.8 `Block.java:808` and 1.8.1 `Block.java:819`.

In the accepted non-source EAST extension phase, current progress is `1.0F` and previous progress is `0.5F`. Old `MovingBlock.getCollisionShape` (1.8.9 `MovingBlock.java:118–130,162–193`) trims the destination-owned box to local X `[0,0.5]`. Exact 1.9 `MovingBlockEntity.getShape` (75–88) unions current and previous shapes to `[-0.5,1]`. 26.2 `PistonMovingBlockEntity.getCollisionShape` (366–393) uses current progress and produces `[0,1]`.

The reviewed horizontal negative-X sweep visits the destination cell, begins ahead of local X=1, and ends strictly between X=0.5 and X=1. The old half box remains outside that sweep and the request stays unchanged. The newer full face clips the request. The existing moving-piston shape operation selects this old plate for the admitted dry ordinary movement query and preserves the exact Glass state.

The 1.8.9 endpoint author SHA-256 is `cab075c9b3e03281f90a58c06b9addbf231967206435f53bd183c7a24fb62699`. The earlier-alias author SHA-256 is `342ea1162226e2c0ce3b9dd2aceb58e120196a3d054790b76a4be552403cc0fa` and the independent alias acceptance SHA-256 is `039279ee506f10c7f5b2eabb6e0922f7abb2ae097e8b227763422d7768f312bb`. The latter verifies each exact release from 1.8 through 1.8.8 against the separately accepted 1.8.9 endpoint. Runtime parity remains unverified.
