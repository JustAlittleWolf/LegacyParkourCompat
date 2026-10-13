# F-007: Local player sends position packets at a finer displacement threshold

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, `sendPosition`, lines 240–269.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `sendPosition`, lines 232–261.

Difference: 1.17.1 LocalPlayer#sendPosition, lines 240-2690E-4, with positionReminder >= 20 as the periodic fallback. 1.18.2 LocalPlayer#sendPosition, lines 232-261lengthSquared(delta) to Mth.square(2.0E-4) = 4.0E-8; Mth methods are lines 739-741 and 799-801 Both send an absolute position packet and reset the same local position baseline after sending. For a controlled-camera, non-passenger local player before the 20-tick reminder, any squared displacement strictly between 4.0E-8 and 9.0E-4 sends a position packet in 1.18.2 and no position packet in 1.17.1.

Movement meaning: Choose an on-foot client whose current displacement from its last-sent baseline is 0.001 blocks along an unobstructed horizontal path, with controlled-camera true, positionReminder below 20, and no passenger, sleep, teleport, or world-collision condition.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-007-position-packet-threshold.md).
