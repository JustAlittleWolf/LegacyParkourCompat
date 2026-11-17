# Independent correction audit verdict

**ACCEPT**

Audited correction: `dependencies/snow-layer-callers-r2.md`, SHA-256 `81319ee440bf5899ebf1542d333dbd1842393cade26f6c734b231fe4c735be85`. Its referenced original report matches SHA-256 `7226b06fb29902ca0296c183f10834fb754fbd8af087ff45d9e4551dcd89807b`; the prior `REQUEST CHANGES` audit matches SHA-256 `90fe324dd3d11b00e0bfb36ab3c0abadbe1af743b4fb1d8f619cd8b1a6c07ee8`.

The exact `BedItem.java` source in both `decompiled_minecraft/1.10.2/ornithe-feather/` (SHA-256 `88fb749df9b9f8dc21f2d0144bca0fdbbbb761577625c7d868d1ce5859d9773b9`) and `decompiled_minecraft/1.11/ornithe-feather/` (SHA-256 `36c14ae0a47a1636c78de69224ae939669f69cb4ae5e2cfd673cb62a465fbb54`) confirms that `BedItem.useOnBlock` checks `isFullBlock()` for the supporting states below both bed positions before placing the bed. This is a player-triggered bed-placement eligibility check and is out of scope for player movement.

The correction appropriately adds this omitted caller family to the inventory and explicitly classifies it without claiming it as an eligible movement caller. The reviewed exact `SnowLayerBlock` sources retain the accepted conclusions: `isFullBlock` changes from 7 layers to 8, with no demonstrated eligible local-client movement caller; the collision-height path remains unchanged and player-reachable; and the snow-on-snow threshold change remains placement-only. The correction does not broaden those claims or pair coverage. The bounded correction resolves the inventory defect identified by the prior audit.