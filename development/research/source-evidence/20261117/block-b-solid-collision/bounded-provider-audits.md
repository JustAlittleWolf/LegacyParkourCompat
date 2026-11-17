# Bounded solid collision provider audits

## Stationary piston base

Independent reviewer `/root/piston_base_provider_audit` accepted the corrected stationary `PistonBaseBlock` result for ordinary movement reads. Ornithe Feather Gen2 build 2 1.8.9 refreshes mutable bounds at each collision query. Starting in 1.9, the queried state selects the same six extended boxes or the retracted full cube. The 1.9.4 provider retains this behavior. Both 26.2 stationary registrations use the same shape table and state selection. The earlier claim of an inherited full collision cube for extended 1.9.4 bases is rejected by the class's explicit `getShape` and `addCollisions`. The stationary occupied shape requires no versioned replacement in this scope.

`PistonBaseBlock.java` SHA-256 for 1.8.9 `3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929`, 1.9 `d2b396408dc26daf9fba18ccac8cc1bfa4bc197042c2629e8df062a66d61f639`, 1.9.4 `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36`, and 26.2 unobfuscated `5287608eaedbd6402f4c6fac93645cf595631b1d2b3dd6987789225045a64a91`. The 1.8.9 and 1.9.4 hashes match their published Feather source manifests. 26.2 `PistonBaseBlock.getShape` and constants are at lines 55 and 63–71. Its `Blocks.java` registers sticky and ordinary bases at 715 and 827.

## Cauldron floor

Independent reviewer `/root/cauldron_geometry_audit` accepted the ordinary collision geometry in exact 1.13, 1.13.1, 1.13.2, 1.14 and 1.14.4 Ornithe Feather Gen2 build 2. The old `CauldronBlock.getShape` uses a full cube minus its upper cavity `(2,4,2)-(14,16,14)` for all LEVEL states. Beginning in 1.14, it adds three bottom cutouts at Y=0..3 to the shape being subtracted. The 1.14.4 construction retains these cutouts. In 26.2, empty and water cauldrons inherit the same ordinary shape from `AbstractCauldronBlock.getShape`. Its entity-inside geometry and modern-only powder-snow and lava states are separate.

`CauldronBlock.java` SHA-256 for 1.13 `81cf1275a962c024bcfdf569286e967088f2f8d06a6ab5e8eb88b8bad7ab33c8`, 1.13.1/1.13.2 `6948b2e208e1d7a52123fece400aa717b6fa5c35189fff1137490a96c170a3ac`, 1.14 `9087aa4b5a23828e50ba2bfff1853e2419c65a9c1a3fcae95a5a7b541ce00ad1`, and 1.14.4 `686519b540dcb313c5226ebae2f77e902eb1d5b76997ba8fdf8cd37ab772179e`. The corresponding members are 32–45 through 1.13.2 and 35–52 in 1.14. Modern `AbstractCauldronBlock.java` SHA-256 `a4e10cccf88b3c0d1cc64f33a06d8ad39c28f6b823483b910e71b9bd16191492`, members at 26–40 and 69–76. Existing `change/v1_13/CauldronCollisionShape.java` uses the old shape and registers exactly the empty and water states for the supported 1.13 line.

## Player edge support probe

Independent reviewer `/root/edge_support_boundary_audit` accepted the exact 1.21.4 → 1.21.5 Mojmap method contract and 26.2 native behavior. 1.21.4 `Player.canFallAtLeast` takes float minHeight and subtracts it followed by `1.0E-5F`; it uses the full horizontal span. 1.21.5 takes double minHeight, applies `1.0E-7` horizontal insets and subtracts `1.0E-7` on the lower Y bound. The step-height operand and the `isAboveGround` subtraction remain float expressions at the caller. 26.2 retains this operation. A strictly positive vertical overlap and a horizontal overlap narrower than the new inset can flip full-cube support. Existing `change/v1_21_4/EdgeBackoffProbeBounds.java` restores the old query's float conversion and coordinate arithmetic through its resolved client hook.

`Player.java` SHA-256 for 1.21.4 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`, 1.21.5 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`, and 26.2 unobfuscated `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`. The cited caller and helper ranges are 1069–1126, 1026–1087, and 880–943 respectively.

These verdicts address the named source operations and static product correspondence. Runtime parity and full-pair coverage remain open.
