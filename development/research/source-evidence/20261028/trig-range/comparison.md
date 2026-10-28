# Relative input trig source comparison

Read-only source inspection. Root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; all source trees are in the Mojmap namespace. This is a bounded trig/source comparison, not a complete player-tick inventory.

## Publication and file identities

| Version | Mapping metadata | Source manifest SHA-256 (declared) | Mth.java SHA-256 | Entity.java SHA-256 |
|---|---|---|---|---|
| 1.16.2 | mojmap provenance + ready present | 10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7 | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| 1.16.3 | root markers absent; embedded provenance present | embedded source hashes verified | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| 1.16.4 | root markers absent; embedded provenance present | embedded source hashes verified | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| 1.16.5 | mojmap provenance + ready present | 9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b | 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290 | f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666 |
| 1.17 | root markers absent; embedded provenance present | embedded source hashes verified | 24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113 | 81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef |
| 1.17.1 | mojmap provenance + ready present | 93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b | 24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113 | ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de |
| 1.18.2 | mojmap provenance + ready present | aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a | 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e | 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a |
| 1.19.4 | mojmap provenance + ready present | 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 | 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797 | 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b |
| 1.20.2 | mojmap provenance + ready present | e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887 | 8a16560d3dd84853a2caaa658d6a106ea2e7f4e04d5cd6ed4d8f76839cda9dce | d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b |
| 1.20.4 | mojmap provenance + ready present | fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1 | f211066a32f5fd2ff3605d0db66f79d7811d7c90ea205caf6b5ce957c0652075 | 07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9 |
| 1.20.5 | mojmap provenance + ready present | 162dac6c4f1d539a1c2cea0255b7938a5281c207ab460bdc265358016d732484 | f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d | 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3 |
| 1.20.6 | mojmap provenance + ready present | 56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311 | f1b2d62f18b4b20ffad5843f40d34d8b5f9c40eee3e106dfe275859a24cfb18d | 71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3 |
| 1.21.1 | mojmap provenance + ready present | 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 | aa40272f0f980438ca7e8c874ceedc664feb77958b162d7aa3ed01f233bd2deb | b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850 |
| 1.21.2 | root markers absent; embedded provenance present | embedded source hashes verified | 98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 |
| 1.21.3 | mojmap provenance + ready present | d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce | 98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da | a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9 |
| 1.21.4 | mojmap provenance + ready present | f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0 | 98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da | 05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad |
| 1.21.5 | mojmap provenance + ready present | 365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9 | 98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da | fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4 |
| 1.21.6 | root markers absent; embedded provenance present | embedded source hashes verified | 962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b | c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704 |
| 1.21.7 | root markers absent; embedded provenance present | embedded source hashes verified | 962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b | c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704 |
| 1.21.8 | mojmap provenance + ready present | 8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008 | 962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b | c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704 |
| 1.21.10 | mojmap provenance + ready present | 4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1 | d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1 | 8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18 |
| 1.21.11 | mojmap provenance + ready present | 0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555 | 1b547c6b896c9b149d4bc03e3ed106ece485d7ac481a792ae30f600d0239726c | 32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3 |

Root `mojmap.provenance.json` and `mojmap.ready.json` are absent for six releases, but each has a hidden `mojmap/.decompile-provenance.json`. Its pinned Minecraft client, `client.txt` mappings, and version-metadata inputs are recorded below, and its embedded Mth/Entity source hashes match the files. Thus these six identities are established from their embedded provenance, not inferred from neighboring sources.

### Embedded provenance for releases without root markers

| Version | Hidden provenance file SHA-256 | Minecraft client JAR SHA-256 | `client.txt` SHA-256 | Version metadata SHA-256 | Embedded Mth/Entity hashes match |
|---|---|---|---|---|---|
| 1.16.3 | 513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498 | da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa | c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e | 193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead | yes |
| 1.16.4 | 1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c | d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0 | 93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe | 667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5 | yes |
| 1.17 | afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357 | 14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f | 191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471 | 537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6 | yes |
| 1.21.2 | 00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575 | 613091da0f62426ae8dcf9afb30eb639485f493b81cda3a2b96b1fb8a6b9976c | e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2 | 793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45 | yes |
| 1.21.6 | 56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e | 9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a | 2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261 | ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd | yes |
| 1.21.7 | 45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d | 6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9 | 328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50 | b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a | yes |

### 1.16.2

Mth lookup generation and sin/cos entry points:
```java
12:     private static final float[] SIN = Util.make(new float[65536], fs -> {
13:         for (int ix = 0; ix < fs.length; ix++) {
14:             fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0);
15:         }
16:     });
17:     private static final Random RANDOM = new Random();
18:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
19:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
20:     };
21:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
22:     private static final double[] ASIN_TAB = new double[257];
23:     private static final double[] COS_TAB = new double[257];
24: 
25:     public static float sin(float f) {
26:         return SIN[(int)(f * 10430.378F) & 65535];
27:     }
28: 
29:     public static float cos(float f) {
30:         return SIN[(int)(f * 10430.378F + 16384.0F) & 65535];
31:     }
```
Entity movement caller and input rotation math:
```java
1075:     public void moveRelative(float f, Vec3 vec3) {
1076:         Vec3 vec32 = getInputVector(vec3, f, this.yRot);
1077:         this.setDeltaMovement(this.getDeltaMovement().add(vec32));
1078:     }
1079: 
1080:     private static Vec3 getInputVector(Vec3 vec3, float f, float g) {
1081:         double d = vec3.lengthSqr();
1082:         if (d < 1.0E-7) {
1083:             return Vec3.ZERO;
1084:         }
1085: 
1086:         Vec3 vec32 = (d > 1.0 ? vec3.normalize() : vec3).scale(f);
1087:         float h = Mth.sin(g * (float) (Math.PI / 180.0));
1088:         float i = Mth.cos(g * (float) (Math.PI / 180.0));
1089:         return new Vec3(vec32.x * i - vec32.z * h, vec32.y, vec32.z * i + vec32.x * h);
1090:     }
1091: 
1092:     public float getBrightness() {
1093:         BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(this.getX(), 0.0, this.getZ());
1094:         if (this.level.hasChunkAt(mutableBlockPos)) {
```

### 1.16.3

Mth lookup generation and sin/cos entry points:
```java
12:     private static final float[] SIN = Util.make(new float[65536], fs -> {
13:         for (int ix = 0; ix < fs.length; ix++) {
14:             fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0);
15:         }
16:     });
17:     private static final Random RANDOM = new Random();
18:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
19:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
20:     };
21:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
22:     private static final double[] ASIN_TAB = new double[257];
23:     private static final double[] COS_TAB = new double[257];
24: 
25:     public static float sin(float f) {
26:         return SIN[(int)(f * 10430.378F) & 65535];
27:     }
28: 
29:     public static float cos(float f) {
30:         return SIN[(int)(f * 10430.378F + 16384.0F) & 65535];
31:     }
```
Entity movement caller and input rotation math:
```java
1075:     public void moveRelative(float f, Vec3 vec3) {
1076:         Vec3 vec32 = getInputVector(vec3, f, this.yRot);
1077:         this.setDeltaMovement(this.getDeltaMovement().add(vec32));
1078:     }
1079: 
1080:     private static Vec3 getInputVector(Vec3 vec3, float f, float g) {
1081:         double d = vec3.lengthSqr();
1082:         if (d < 1.0E-7) {
1083:             return Vec3.ZERO;
1084:         }
1085: 
1086:         Vec3 vec32 = (d > 1.0 ? vec3.normalize() : vec3).scale(f);
1087:         float h = Mth.sin(g * (float) (Math.PI / 180.0));
1088:         float i = Mth.cos(g * (float) (Math.PI / 180.0));
1089:         return new Vec3(vec32.x * i - vec32.z * h, vec32.y, vec32.z * i + vec32.x * h);
1090:     }
1091: 
1092:     public float getBrightness() {
1093:         BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(this.getX(), 0.0, this.getZ());
1094:         if (this.level.hasChunkAt(mutableBlockPos)) {
```

### 1.16.4

Mth lookup generation and sin/cos entry points:
```java
12:     private static final float[] SIN = Util.make(new float[65536], fs -> {
13:         for (int ix = 0; ix < fs.length; ix++) {
14:             fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0);
15:         }
16:     });
17:     private static final Random RANDOM = new Random();
18:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
19:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
20:     };
21:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
22:     private static final double[] ASIN_TAB = new double[257];
23:     private static final double[] COS_TAB = new double[257];
24: 
25:     public static float sin(float f) {
26:         return SIN[(int)(f * 10430.378F) & 65535];
27:     }
28: 
29:     public static float cos(float f) {
30:         return SIN[(int)(f * 10430.378F + 16384.0F) & 65535];
31:     }
```
Entity movement caller and input rotation math:
```java
1075:     public void moveRelative(float f, Vec3 vec3) {
1076:         Vec3 vec32 = getInputVector(vec3, f, this.yRot);
1077:         this.setDeltaMovement(this.getDeltaMovement().add(vec32));
1078:     }
1079: 
1080:     private static Vec3 getInputVector(Vec3 vec3, float f, float g) {
1081:         double d = vec3.lengthSqr();
1082:         if (d < 1.0E-7) {
1083:             return Vec3.ZERO;
1084:         }
1085: 
1086:         Vec3 vec32 = (d > 1.0 ? vec3.normalize() : vec3).scale(f);
1087:         float h = Mth.sin(g * (float) (Math.PI / 180.0));
1088:         float i = Mth.cos(g * (float) (Math.PI / 180.0));
1089:         return new Vec3(vec32.x * i - vec32.z * h, vec32.y, vec32.z * i + vec32.x * h);
1090:     }
1091: 
1092:     public float getBrightness() {
1093:         BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(this.getX(), 0.0, this.getZ());
1094:         if (this.level.hasChunkAt(mutableBlockPos)) {
```

### 1.16.5

Mth lookup generation and sin/cos entry points:
```java
12:     private static final float[] SIN = Util.make(new float[65536], fs -> {
13:         for (int ix = 0; ix < fs.length; ix++) {
14:             fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0);
15:         }
16:     });
17:     private static final Random RANDOM = new Random();
18:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
19:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
20:     };
21:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
22:     private static final double[] ASIN_TAB = new double[257];
23:     private static final double[] COS_TAB = new double[257];
24: 
25:     public static float sin(float f) {
26:         return SIN[(int)(f * 10430.378F) & 65535];
27:     }
28: 
29:     public static float cos(float f) {
30:         return SIN[(int)(f * 10430.378F + 16384.0F) & 65535];
31:     }
```
Entity movement caller and input rotation math:
```java
1075:     public void moveRelative(float f, Vec3 vec3) {
1076:         Vec3 vec32 = getInputVector(vec3, f, this.yRot);
1077:         this.setDeltaMovement(this.getDeltaMovement().add(vec32));
1078:     }
1079: 
1080:     private static Vec3 getInputVector(Vec3 vec3, float f, float g) {
1081:         double d = vec3.lengthSqr();
1082:         if (d < 1.0E-7) {
1083:             return Vec3.ZERO;
1084:         }
1085: 
1086:         Vec3 vec32 = (d > 1.0 ? vec3.normalize() : vec3).scale(f);
1087:         float h = Mth.sin(g * (float) (Math.PI / 180.0));
1088:         float i = Mth.cos(g * (float) (Math.PI / 180.0));
1089:         return new Vec3(vec32.x * i - vec32.z * h, vec32.y, vec32.z * i + vec32.x * h);
1090:     }
1091: 
1092:     public float getBrightness() {
1093:         BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(this.getX(), 0.0, this.getZ());
1094:         if (this.level.hasChunkAt(mutableBlockPos)) {
```

### 1.17

Mth lookup generation and sin/cos entry points:
```java
27:     private static final float[] SIN = Util.make(new float[65536], fs -> {
28:         for (int ix = 0; ix < fs.length; ix++) {
29:             fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0);
30:         }
31:     });
32:     private static final Random RANDOM = new Random();
33:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
34:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
35:     };
36:     private static final double ONE_SIXTH = 0.16666666666666666;
37:     private static final int FRAC_EXP = 8;
38:     private static final int LUT_SIZE = 257;
39:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
40:     private static final double[] ASIN_TAB = new double[257];
41:     private static final double[] COS_TAB = new double[257];
42: 
43:     public static float sin(float f) {
44:         return SIN[(int)(f * 10430.378F) & 65535];
45:     }
46: 
47:     public static float cos(float f) {
48:         return SIN[(int)(f * 10430.378F + 16384.0F) & 65535];
49:     }
```
Entity movement caller and input rotation math:
```java
1174:     public void moveRelative(float f, Vec3 vec3) {
1175:         Vec3 vec32 = getInputVector(vec3, f, this.getYRot());
1176:         this.setDeltaMovement(this.getDeltaMovement().add(vec32));
1177:     }
1178: 
1179:     private static Vec3 getInputVector(Vec3 vec3, float f, float g) {
1180:         double d = vec3.lengthSqr();
1181:         if (d < 1.0E-7) {
1182:             return Vec3.ZERO;
1183:         }
1184: 
1185:         Vec3 vec32 = (d > 1.0 ? vec3.normalize() : vec3).scale(f);
1186:         float h = Mth.sin(g * (float) (Math.PI / 180.0));
1187:         float i = Mth.cos(g * (float) (Math.PI / 180.0));
1188:         return new Vec3(vec32.x * i - vec32.z * h, vec32.y, vec32.z * i + vec32.x * h);
1189:     }
1190: 
1191:     public float getBrightness() {
1192:         return this.level.hasChunkAt(this.getBlockX(), this.getBlockZ())
1193:             ? this.level.getBrightness(new BlockPos(this.getX(), this.getEyeY(), this.getZ()))
```

### 1.17.1

Mth lookup generation and sin/cos entry points:
```java
27:     private static final float[] SIN = Util.make(new float[65536], fs -> {
28:         for (int ix = 0; ix < fs.length; ix++) {
29:             fs[ix] = (float)Math.sin(ix * Math.PI * 2.0 / 65536.0);
30:         }
31:     });
32:     private static final Random RANDOM = new Random();
33:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
34:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
35:     };
36:     private static final double ONE_SIXTH = 0.16666666666666666;
37:     private static final int FRAC_EXP = 8;
38:     private static final int LUT_SIZE = 257;
39:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
40:     private static final double[] ASIN_TAB = new double[257];
41:     private static final double[] COS_TAB = new double[257];
42: 
43:     public static float sin(float f) {
44:         return SIN[(int)(f * 10430.378F) & 65535];
45:     }
46: 
47:     public static float cos(float f) {
48:         return SIN[(int)(f * 10430.378F + 16384.0F) & 65535];
49:     }
```
Entity movement caller and input rotation math:
```java
1176:     public void moveRelative(float f, Vec3 vec3) {
1177:         Vec3 vec32 = getInputVector(vec3, f, this.getYRot());
1178:         this.setDeltaMovement(this.getDeltaMovement().add(vec32));
1179:     }
1180: 
1181:     private static Vec3 getInputVector(Vec3 vec3, float f, float g) {
1182:         double d = vec3.lengthSqr();
1183:         if (d < 1.0E-7) {
1184:             return Vec3.ZERO;
1185:         }
1186: 
1187:         Vec3 vec32 = (d > 1.0 ? vec3.normalize() : vec3).scale(f);
1188:         float h = Mth.sin(g * (float) (Math.PI / 180.0));
1189:         float i = Mth.cos(g * (float) (Math.PI / 180.0));
1190:         return new Vec3(vec32.x * i - vec32.z * h, vec32.y, vec32.z * i + vec32.x * h);
1191:     }
1192: 
1193:     public float getBrightness() {
1194:         return this.level.hasChunkAt(this.getBlockX(), this.getBlockZ())
1195:             ? this.level.getBrightness(new BlockPos(this.getX(), this.getEyeY(), this.getZ()))
```

### 1.18.2

Mth lookup generation and sin/cos entry points:
```java
27:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
28:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
29:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
30:         }
31:     });
32:     private static final Random RANDOM = new Random();
33:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
34:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
35:     };
36:     private static final double ONE_SIXTH = 0.16666666666666666;
37:     private static final int FRAC_EXP = 8;
38:     private static final int LUT_SIZE = 257;
39:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
40:     private static final double[] ASIN_TAB = new double[257];
41:     private static final double[] COS_TAB = new double[257];
42: 
43:     public static float sin(float $$0) {
44:         return SIN[(int)($$0 * 10430.378F) & 65535];
45:     }
46: 
47:     public static float cos(float $$0) {
48:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
49:     }
```
Entity movement caller and input rotation math:
```java
1151:     public void moveRelative(float $$0, Vec3 $$1) {
1152:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1153:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1154:     }
1155: 
1156:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1157:         double $$3 = $$0.lengthSqr();
1158:         if ($$3 < 1.0E-7) {
1159:             return Vec3.ZERO;
1160:         }
1161: 
1162:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1163:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1164:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1165:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1166:     }
1167: 
1168:     public float getBrightness() {
1169:         return this.level.hasChunkAt(this.getBlockX(), this.getBlockZ())
1170:             ? this.level.getBrightness(new BlockPos(this.getX(), this.getEyeY(), this.getZ()))
```

### 1.19.4

Mth lookup generation and sin/cos entry points:
```java
26:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
27:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
28:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
29:         }
30:     });
31:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
32:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
33:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
34:     };
35:     private static final double ONE_SIXTH = 0.16666666666666666;
36:     private static final int FRAC_EXP = 8;
37:     private static final int LUT_SIZE = 257;
38:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
39:     private static final double[] ASIN_TAB = new double[257];
40:     private static final double[] COS_TAB = new double[257];
41: 
42:     public static float sin(float $$0) {
43:         return SIN[(int)($$0 * 10430.378F) & 65535];
44:     }
45: 
46:     public static float cos(float $$0) {
47:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
48:     }
```
Entity movement caller and input rotation math:
```java
1181:     public void moveRelative(float $$0, Vec3 $$1) {
1182:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1183:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1184:     }
1185: 
1186:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1187:         double $$3 = $$0.lengthSqr();
1188:         if ($$3 < 1.0E-7) {
1189:             return Vec3.ZERO;
1190:         }
1191: 
1192:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1193:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1194:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1195:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1196:     }
1197: 
1198:     @Deprecated
1199:     public float getLightLevelDependentMagicValue() {
1200:         return this.level.hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.20.2

Mth lookup generation and sin/cos entry points:
```java
26:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
27:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
28:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
29:         }
30:     });
31:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
32:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
33:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
34:     };
35:     private static final double ONE_SIXTH = 0.16666666666666666;
36:     private static final int FRAC_EXP = 8;
37:     private static final int LUT_SIZE = 257;
38:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
39:     private static final double[] ASIN_TAB = new double[257];
40:     private static final double[] COS_TAB = new double[257];
41: 
42:     public static float sin(float $$0) {
43:         return SIN[(int)($$0 * 10430.378F) & 65535];
44:     }
45: 
46:     public static float cos(float $$0) {
47:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
48:     }
```
Entity movement caller and input rotation math:
```java
1294:     public void moveRelative(float $$0, Vec3 $$1) {
1295:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1296:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1297:     }
1298: 
1299:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1300:         double $$3 = $$0.lengthSqr();
1301:         if ($$3 < 1.0E-7) {
1302:             return Vec3.ZERO;
1303:         }
1304: 
1305:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1306:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1307:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1308:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1309:     }
1310: 
1311:     @Deprecated
1312:     public float getLightLevelDependentMagicValue() {
1313:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.20.4

Mth lookup generation and sin/cos entry points:
```java
31:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
32:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
33:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
34:         }
35:     });
36:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
37:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
38:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
39:     };
40:     private static final double ONE_SIXTH = 0.16666666666666666;
41:     private static final int FRAC_EXP = 8;
42:     private static final int LUT_SIZE = 257;
43:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
44:     private static final double[] ASIN_TAB = new double[257];
45:     private static final double[] COS_TAB = new double[257];
46: 
47:     public static float sin(float $$0) {
48:         return SIN[(int)($$0 * 10430.378F) & 65535];
49:     }
50: 
51:     public static float cos(float $$0) {
52:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
53:     }
```
Entity movement caller and input rotation math:
```java
1295:     public void moveRelative(float $$0, Vec3 $$1) {
1296:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1297:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1298:     }
1299: 
1300:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1301:         double $$3 = $$0.lengthSqr();
1302:         if ($$3 < 1.0E-7) {
1303:             return Vec3.ZERO;
1304:         }
1305: 
1306:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1307:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1308:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1309:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1310:     }
1311: 
1312:     @Deprecated
1313:     public float getLightLevelDependentMagicValue() {
1314:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.20.5

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1311:     public void moveRelative(float $$0, Vec3 $$1) {
1312:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1313:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1314:     }
1315: 
1316:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1317:         double $$3 = $$0.lengthSqr();
1318:         if ($$3 < 1.0E-7) {
1319:             return Vec3.ZERO;
1320:         }
1321: 
1322:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1323:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1324:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1325:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1326:     }
1327: 
1328:     @Deprecated
1329:     public float getLightLevelDependentMagicValue() {
1330:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.20.6

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1311:     public void moveRelative(float $$0, Vec3 $$1) {
1312:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1313:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1314:     }
1315: 
1316:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1317:         double $$3 = $$0.lengthSqr();
1318:         if ($$3 < 1.0E-7) {
1319:             return Vec3.ZERO;
1320:         }
1321: 
1322:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1323:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1324:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1325:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1326:     }
1327: 
1328:     @Deprecated
1329:     public float getLightLevelDependentMagicValue() {
1330:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.1

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1350:     public void moveRelative(float $$0, Vec3 $$1) {
1351:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1352:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1353:     }
1354: 
1355:     private static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1356:         double $$3 = $$0.lengthSqr();
1357:         if ($$3 < 1.0E-7) {
1358:             return Vec3.ZERO;
1359:         }
1360: 
1361:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1362:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1363:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1364:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1365:     }
1366: 
1367:     @Deprecated
1368:     public float getLightLevelDependentMagicValue() {
1369:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.2

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1403:     public void moveRelative(float $$0, Vec3 $$1) {
1404:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1405:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1406:     }
1407: 
1408:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1409:         double $$3 = $$0.lengthSqr();
1410:         if ($$3 < 1.0E-7) {
1411:             return Vec3.ZERO;
1412:         }
1413: 
1414:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1415:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1416:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1417:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1418:     }
1419: 
1420:     @Deprecated
1421:     public float getLightLevelDependentMagicValue() {
1422:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.3

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1403:     public void moveRelative(float $$0, Vec3 $$1) {
1404:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1405:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1406:     }
1407: 
1408:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1409:         double $$3 = $$0.lengthSqr();
1410:         if ($$3 < 1.0E-7) {
1411:             return Vec3.ZERO;
1412:         }
1413: 
1414:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1415:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1416:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1417:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1418:     }
1419: 
1420:     @Deprecated
1421:     public float getLightLevelDependentMagicValue() {
1422:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.4

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1424:     public void moveRelative(float $$0, Vec3 $$1) {
1425:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1426:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1427:     }
1428: 
1429:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1430:         double $$3 = $$0.lengthSqr();
1431:         if ($$3 < 1.0E-7) {
1432:             return Vec3.ZERO;
1433:         }
1434: 
1435:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1436:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1437:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1438:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1439:     }
1440: 
1441:     @Deprecated
1442:     public float getLightLevelDependentMagicValue() {
1443:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.5

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1453:     public void moveRelative(float $$0, Vec3 $$1) {
1454:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1455:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1456:     }
1457: 
1458:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1459:         double $$3 = $$0.lengthSqr();
1460:         if ($$3 < 1.0E-7) {
1461:             return Vec3.ZERO;
1462:         }
1463: 
1464:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1465:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1466:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1467:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1468:     }
1469: 
1470:     @Deprecated
1471:     public float getLightLevelDependentMagicValue() {
1472:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.6

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1530:     public void moveRelative(float $$0, Vec3 $$1) {
1531:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1532:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1533:     }
1534: 
1535:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1536:         double $$3 = $$0.lengthSqr();
1537:         if ($$3 < 1.0E-7) {
1538:             return Vec3.ZERO;
1539:         }
1540: 
1541:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1542:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1543:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1544:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1545:     }
1546: 
1547:     @Deprecated
1548:     public float getLightLevelDependentMagicValue() {
1549:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.7

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1530:     public void moveRelative(float $$0, Vec3 $$1) {
1531:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1532:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1533:     }
1534: 
1535:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1536:         double $$3 = $$0.lengthSqr();
1537:         if ($$3 < 1.0E-7) {
1538:             return Vec3.ZERO;
1539:         }
1540: 
1541:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1542:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1543:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1544:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1545:     }
1546: 
1547:     @Deprecated
1548:     public float getLightLevelDependentMagicValue() {
1549:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.8

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1530:     public void moveRelative(float $$0, Vec3 $$1) {
1531:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1532:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1533:     }
1534: 
1535:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1536:         double $$3 = $$0.lengthSqr();
1537:         if ($$3 < 1.0E-7) {
1538:             return Vec3.ZERO;
1539:         }
1540: 
1541:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1542:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1543:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1544:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1545:     }
1546: 
1547:     @Deprecated
1548:     public float getLightLevelDependentMagicValue() {
1549:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.10

Mth lookup generation and sin/cos entry points:
```java
32:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
33:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
34:             $$0x[$$1x] = (float)Math.sin($$1x * Math.PI * 2.0 / 65536.0);
35:         }
36:     });
37:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
38:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
39:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
40:     };
41:     private static final double ONE_SIXTH = 0.16666666666666666;
42:     private static final int FRAC_EXP = 8;
43:     private static final int LUT_SIZE = 257;
44:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
45:     private static final double[] ASIN_TAB = new double[257];
46:     private static final double[] COS_TAB = new double[257];
47: 
48:     public static float sin(float $$0) {
49:         return SIN[(int)($$0 * 10430.378F) & 65535];
50:     }
51: 
52:     public static float cos(float $$0) {
53:         return SIN[(int)($$0 * 10430.378F + 16384.0F) & 65535];
54:     }
```
Entity movement caller and input rotation math:
```java
1590:     public void moveRelative(float $$0, Vec3 $$1) {
1591:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1592:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1593:     }
1594: 
1595:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1596:         double $$3 = $$0.lengthSqr();
1597:         if ($$3 < 1.0E-7) {
1598:             return Vec3.ZERO;
1599:         }
1600: 
1601:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1602:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1603:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1604:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1605:     }
1606: 
1607:     @Deprecated
1608:     public float getLightLevelDependentMagicValue() {
1609:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

### 1.21.11

Mth lookup generation and sin/cos entry points:
```java
34:     private static final float[] SIN = Util.make(new float[65536], $$0x -> {
35:         for (int $$1x = 0; $$1x < $$0x.length; $$1x++) {
36:             $$0x[$$1x] = (float)Math.sin($$1x / 10430.378350470453);
37:         }
38:     });
39:     private static final RandomSource RANDOM = RandomSource.createThreadSafe();
40:     private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
41:         0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
42:     };
43:     private static final double ONE_SIXTH = 0.16666666666666666;
44:     private static final int FRAC_EXP = 8;
45:     private static final int LUT_SIZE = 257;
46:     private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
47:     private static final double[] ASIN_TAB = new double[257];
48:     private static final double[] COS_TAB = new double[257];
49: 
50:     public static float sin(double $$0) {
51:         return SIN[(int)((long)($$0 * 10430.378350470453) & 65535L)];
52:     }
53: 
54:     public static float cos(double $$0) {
55:         return SIN[(int)((long)($$0 * 10430.378350470453 + 16384.0) & 65535L)];
56:     }
```
Entity movement caller and input rotation math:
```java
1608:     public void moveRelative(float $$0, Vec3 $$1) {
1609:         Vec3 $$2 = getInputVector($$1, $$0, this.getYRot());
1610:         this.setDeltaMovement(this.getDeltaMovement().add($$2));
1611:     }
1612: 
1613:     protected static Vec3 getInputVector(Vec3 $$0, float $$1, float $$2) {
1614:         double $$3 = $$0.lengthSqr();
1615:         if ($$3 < 1.0E-7) {
1616:             return Vec3.ZERO;
1617:         }
1618: 
1619:         Vec3 $$4 = ($$3 > 1.0 ? $$0.normalize() : $$0).scale($$1);
1620:         float $$5 = Mth.sin($$2 * (float) (Math.PI / 180.0));
1621:         float $$6 = Mth.cos($$2 * (float) (Math.PI / 180.0));
1622:         return new Vec3($$4.x * $$6 - $$4.z * $$5, $$4.y, $$4.z * $$6 + $$4.x * $$5);
1623:     }
1624: 
1625:     @Deprecated
1626:     public float getLightLevelDependentMagicValue() {
1627:         return this.level().hasChunkAt(this.getBlockX(), this.getBlockZ())
```

## Comparison summary

Every listed source directory through 1.21.10 has a float `Mth.sin(float)` and `Mth.cos(float)` lookup over `float[65536]`. Across these samples, the lookup computes an `int` index from the float expression using `10430.378F` and `65535`; `Entity#getInputVector` supplies a float radians expression from its float yaw and uses the results for X/Z rotation. The 1.21.11 directory changes the Mth overloads to `double`; its index expression uses `10430.378350470453`, a `long` cast and `65535L`, while the table remains `float[65536]` and its initializer uses a changed double expression. The per-release excerpts preserve the exact line numbers and nearby call path.

For every release with root-level ready metadata, its declared source-manifest digest matched the on-disk `mojmap.sources.sha256` file, and its entries for `Mth.java` and `Entity.java` matched the computed source hashes in the table. Six releases lack those root-level markers; their hidden provenance records identify version-specific Minecraft client, mapping and version-metadata inputs, and the embedded hashes for both source files match. The full assigned list therefore has checked release input identities, including the six releases without root markers.

The excerpt around `moveRelative` may include the next method signature after `getInputVector`; only `moveRelative` and `getInputVector` are in scope here. No other movement tick slices were inventoried.

Coverage boundary: 1.21.9 was not in the assignment and was not inspected, so the result establishes the float lookup for each assigned release through 1.21.10, without asserting its state in 1.21.9 or a transition boundary between 1.21.8 and 1.21.10. Releases before 1.16.2 were also outside this assignment; 1.16.2 is the earliest assigned sample, not a claim about the earliest historical release.
For every release with root markers, `provenance.versionId` and `ready.versionId` matched the directory version, and both the provenance namespace and exact-run mapping option were `mojmap`. The available sources were decompiled with Vineflower. For the six embedded-only records, the pinned version metadata input URL names the exact directory release; the `client.jar`, `client.txt` mapping input and `version.json` input hashes are included above. Each record's embedded Mth and Entity hashes matched the source files byte-for-byte.
