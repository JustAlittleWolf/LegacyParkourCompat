# Append-only vanilla tag/dimension resource roster

Applies to the frozen operation roster SHA-256 `D3A172F7F169DA08F8979EA09411F651AD09590D20ABCC83E99DB5E31FCF4FD8` and its corrections/addenda. The authorized caller review establishes movement dependencies on the WATER/LAVA built-in tags and `DimensionType.ultraWarm()`. Freeze these exact raw-client resource members before reading their contents:

- `data/minecraft/tags/fluid/lava.json`
- `data/minecraft/tags/fluid/water.json`
- `data/minecraft/dimension_type/overworld.json`
- `data/minecraft/dimension_type/overworld_caves.json`
- `data/minecraft/dimension_type/the_end.json`
- `data/minecraft/dimension_type/the_nether.json`

Evidence containers are the already-frozen exact raw client jars: 1.21.6 cached jar pin SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`; 1.21.7 cached jar pin SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`. For 1.21.8 the ready/artifact manifests assert raw jar SHA-256 `ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`; report member-byte comparison blocked if that exact jar is not present locally. This dependency addendum is limited to tag membership and dimension-type `ultrawarm` defaults read by the frozen movement calls; no other datapack or resource members are in scope.
