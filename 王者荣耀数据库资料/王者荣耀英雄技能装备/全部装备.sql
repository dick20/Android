use my_db;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hero_equip
-- ----------------------------
DROP TABLE IF EXISTS `equip`;
CREATE TABLE `equip` (
  `equip_id` int(11) NOT NULL,
  `equip_name` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `attr` int(8) NOT NULL,
  `selling_price` int(20) NOT NULL,
  `buying_price` int(20) NOT NULL,
  `detail` text CHARACTER SET utf8,
  `unique_passive` text CHARACTER SET utf8,
  `img_url` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`equip_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='所有装备';



INSERT INTO `equip` VALUES (1111, '铁剑', 1, 150, 250, '<p>+20物理攻击</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1111.jpg');
INSERT INTO `equip` VALUES (1112, '匕首', 1, 174, 290, '<p>+10%攻击速度 </p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1112.jpg');
INSERT INTO `equip` VALUES (1113, '搏击拳套', 1, 192, 320, '<p>+8%暴击率 </p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1113.jpg');
INSERT INTO `equip` VALUES (1114, '吸血之镰', 1, 246, 410, '<p>+10物理攻击<br>+8%物理吸血</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1114.jpg');
INSERT INTO `equip` VALUES (1116, '雷鸣刃', 1, 270, 450, '<p>+40物理攻击</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1116.jpg');
INSERT INTO `equip` VALUES (1117, '冲能拳套', 1, 330, 550, '<p>+15%暴击率</p> ', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1117.jpg');
INSERT INTO `equip` VALUES (1121, '风暴巨剑', 1, 546, 910, '<p>+80物理攻击</p> ', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1121.jpg');
INSERT INTO `equip` VALUES (1122, '日冕', 1, 474, 790, '<p>+40物理攻击<br>+300最大生命</p> ', '<p>唯一被动-残废：普通攻击有30%几率降低敌人20%移动速度，持续2秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1122.jpg');
INSERT INTO `equip` VALUES (1123, '狂暴双刃', 1, 534, 890, '<p>+15%攻击速度<br>+10%暴击率<br>+5%移速</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1123.jpg');
INSERT INTO `equip` VALUES (1124, '陨星', 1, 250, 1080, '<p>+45物理攻击<br>+10%冷却缩减</p>', '<p>唯一被动-切割：+60护甲穿透</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1124.jpg');
INSERT INTO `equip` VALUES (1125, '破魔刀', 1, 910, 2000, '<p>物理攻击+100<br>+50法术防御</p>', '<p>唯一被动-破魔：增加等同于自身物理攻击40%的法术防御，最多增加300点</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1125.jpg');
INSERT INTO `equip` VALUES (1126, '末世', 1, 1296, 2160, '<p>+60物理攻击<br>+30%攻击速度 <br>+10%物理吸血</p>', '<p>唯一被动-破败：普通攻击附带敌人当前生命值8%的物理伤害（对野怪最多：80）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1126.jpg');
INSERT INTO `equip` VALUES (1127, '名刀?司命', 1, 1056, 1760, '<p>+60物理攻击<br>+5%冷却缩减</p>', '<p>唯一被动-暗幕：免疫致命伤并免疫伤害、增加20%移动速度持续1秒近战/0.5秒远程，90秒冷却</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1127.jpg');
INSERT INTO `equip` VALUES (1128, '冰霜长矛', 1, 1182, 1980, '<p>+80物理攻击<br>+600最大生命 </p>', '<p>唯一被动-碎冰：普通攻击会减少目标30%攻击速度和移动速度，持续2秒<br>远程英雄使用时减速效果持续时间衰减为1秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1128.jpg');
INSERT INTO `equip` VALUES (1129, '速击之枪', 1, 534, 890, '<p>+25%攻击速度</p>', '<p>唯一被动-精准：普通攻击伤害提升30点，远程英雄使用时该效果翻倍。</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1129.jpg');
INSERT INTO `equip` VALUES (1131, '碎星锤', 1, 1260, 2100, '<p>+80物理攻击<br>+10%冷却缩减</p>', '<p>唯一被动：+45%物理护甲穿透</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1131.jpg');
INSERT INTO `equip` VALUES (1132, '泣血之刃', 1, 1044, 1740, '<p>+100物理攻击<br>+25%物理吸血 </p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1132.jpg');
INSERT INTO `equip` VALUES (1133, '无尽战刃', 1, 1284, 2140, '<p>+120物理攻击<br>+20%暴击率</p>', '<p>唯一被动：+50%暴击效果</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1133.jpg');
INSERT INTO `equip` VALUES (1134, '宗师之力', 1, 1506, 2100, '<p>+60物理攻击<br>+20%暴击率<br>+400 最大法力<br>+400最大生命</p>', '<p>唯一被动-强击：使用技能后，2秒内提升自身8%移动速度，并使得下次普通攻击造成额外1.0*物理攻击的物理伤害，冷却时间：2秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1134.jpg');
INSERT INTO `equip` VALUES (1135, '闪电匕首', 1, 1104, 1840, '<p>+30%攻击速度<br>+20%暴击率<br>+8%移速</p>', '<p>唯一被动-电弧：普通攻击有30%几率释放连锁闪电，对目标造成（100+0.3AD）点法术伤害（该效果有0.5秒CD），这个伤害可以暴击</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1135.jpg');
INSERT INTO `equip` VALUES (1136, '影刃', 1, 1242, 2070, '<p>+40%攻击速度<br>+20%暴击率<br>+5%移速</p>', '<p>唯一被动-暴风：暴击后提升自身30%攻击速度和10%移动速度，持续2秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1136.jpg');
INSERT INTO `equip` VALUES (1137, '暗影战斧', 1, 1254, 2090, '<p>+85物理攻击<br>+15%冷却缩减<br>+500最大生命</p>', '<p>唯一被动-切割：增加(50+英雄等级*10)点护甲穿透<br>唯一被动-残废：普通攻击有30%几率降低敌人20%移动速度，持续2秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1137.jpg');
INSERT INTO `equip` VALUES (1138, '破军', 1, 1770, 2950, '<p>+200物理攻击 </p>', '<p>唯一被动-破军：目标生命低于50%时伤害提高30%</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1138.jpg');
INSERT INTO `equip` VALUES (1154, '穿云弓', 1, 660, 1100, '<p>物理攻击+40<br>攻击速度+10%</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1154.jpg');
INSERT INTO `equip` VALUES (1155, '破晓', 1, 2040, 3400, '<p>物理攻击+50<br>攻击速度+35%<br>暴击率+15%</p>', '<p>唯一被动-破甲：+22.5%物理穿透（远程英雄使用时效果翻倍）<br>唯一被动：普通攻击伤害提升50点（远程英雄使用时效果翻倍）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1155.jpg');
INSERT INTO `equip` VALUES (1211, '咒术典籍', 2, 180, 300, '<p>+40法术攻击</p> ', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1211.jpg');
INSERT INTO `equip` VALUES (1212, '蓝宝石', 2, 132, 220, '<p>+300最大法力</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1212.jpg');
INSERT INTO `equip` VALUES (1213, '炼金护符', 2, 72, 120, '<p>+10 每5秒回蓝</p> ', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1213.jpg');
INSERT INTO `equip` VALUES (1214, '圣者法典', 2, 300, 500, '<p>+20法术攻击<br>+8%冷却缩减</p> ', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1214.jpg');
INSERT INTO `equip` VALUES (1216, '元素杖', 2, 324, 540, '<p>+80法术攻击</p> ', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1216.jpg');
INSERT INTO `equip` VALUES (1221, '大棒', 2, 492, 820, '<p>+120法术攻击</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1221.jpg');
INSERT INTO `equip` VALUES (1222, '血族之书', 2, 744, 1240, '<p>+75法术攻击<br>+10%冷却缩减</p>', '<p>唯一被动-嗜血：增加20%法术吸血</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1222.jpg');
INSERT INTO `equip` VALUES (1223, '光辉之剑', 2, 468, 730, '<p>+400最大生命<br>+400最大法力</p> ', '<p>唯一被动-强击：使用技能后，5秒内的下一次普通攻击附加50%物理攻击（+30%法术加成）的法术伤害，这个效果有2秒的冷却时间</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1223.jpg');
INSERT INTO `equip` VALUES (1224, '魅影面罩', 2, 612, 1020, '<p>+70法术攻击<br>+300最大生命</p>', '<p>唯一被动：+75法术穿透</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1224.jpg');
INSERT INTO `equip` VALUES (1225, '进化水晶', 2, 432, 720, '<p>+400最大法力<br>+400最大生命</p>', '<p>唯一被动-英勇奖赏：升级后在3秒内回复20%生命与法力值</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1225.jpg');
INSERT INTO `equip` VALUES (1226, '圣杯', 2, 1218, 1930, '<p>+180法术攻击<br>+15%冷却缩减<br>+25每5秒回蓝</p>', '<p>唯一被动-法力源泉：每5秒恢复5%法力值</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1226.jpg');
INSERT INTO `equip` VALUES (1227, '炽热支配者', 2, 1170, 1950, '<p>+180法术攻击<br>+600最大法力<br>+15 每5秒回蓝</p>', '<p>唯一被动-法力护盾：生命值低于30%时，立刻获得一个吸收450-1500（+50%法术加成）伤害的护盾并提升30%移动速度，持续4秒，这个效果有90秒冷却时间</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1227.jpg');
INSERT INTO `equip` VALUES (1229, '破碎圣杯', 2, 540, 900, '<p>+80法术攻击<br>+5%冷却缩减<br>+20 每5秒回蓝</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1229.jpg');
INSERT INTO `equip` VALUES (1231, '虚无法杖', 2, 1266, 2110, '<p>+180法术攻击<br>+500最大生命值</p>', '<p>唯一被动：+45%法术穿透</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1231.jpg');
INSERT INTO `equip` VALUES (1232, '博学者之怒', 2, 1380, 2300, '<p>+240法术攻击</p>', '<p>唯一主动-毁灭：法术攻击提升35%</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1232.jpg');
INSERT INTO `equip` VALUES (1233, '回响之杖', 2, 1260, 2100, '<p>+240法术攻击 <br>+7%移速 </p>', '<p>唯一被动-回响：技能命中会触发小范围爆炸造成50（+50%法术加成）法术伤害，这个效果有5秒CD</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1233.jpg');
INSERT INTO `equip` VALUES (1234, '冰霜法杖', 2, 1260, 2100, '<p>+150法术攻击 <br>+1050最大生命</p>', '<p>唯一被动-结霜：英雄技能造成伤害会附带20%的减速效果，持续2秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1234.jpg');
INSERT INTO `equip` VALUES (1235, '痛苦面具', 2, 1224, 2040, '<p>+140法术攻击<br>+5%冷却缩减<br>+500最大生命</p>', '<p>唯一被动-折磨：技能伤害会造成相当于目标当前生命值8%的法术伤害，这个效果有3秒CD（对野怪伤害上限：200）<br>唯一被动：+75法术穿透</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1235.jpg');
INSERT INTO `equip` VALUES (1236, '巫术法杖', 2, 1272, 2120, '<p>+140法术攻击<br>+400最大生命<br>+400最大法力<br>+8%移速</p>', '<p>唯一被动-强击：使用技能后，5秒内下一次普通攻击附加30%物理攻击（+80%法术加成）的法术伤害，这个效果有2秒冷却时间</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1236.jpg');
INSERT INTO `equip` VALUES (1237, '时之预言', 2, 1254, 2090, '<p>+160法术攻击<br>+600最大法力<br>+800最大生命</p>', '<p>唯一被动-英勇奖赏：升级后在3秒内回复20%生命值与法力值<br>唯一被动-守护：每5点法功提升1点物理和法术防御，最多提升200点</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1237.jpg');
INSERT INTO `equip` VALUES (1238, '贤者之书', 2, 1794, 2990, '<p>+400法术攻击</p>', '<p>唯一被动-刻印：增加1400点生命</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1238.jpg');
INSERT INTO `equip` VALUES (1239, '辉月', 2, 1194, 1990, '<p>+160法术攻击<br>+10%冷却缩减</p>', '<p>唯一主动-月之守护：90秒CD，免疫所有效果，不能移动、攻击和使用技能，持续1.5秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1239.jpg');
INSERT INTO `equip` VALUES (1240, '噬神之书', 2, 1254, 2090, '<p>+180法术攻击<br>+10%冷却缩减<br>+800最大生命</p>', '<p>唯一被动-嗜血：增加25%法术吸血</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1240.jpg');
INSERT INTO `equip` VALUES (1311, '红玛瑙', 3, 180, 300, '<p>+300最大生命</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1311.jpg');
INSERT INTO `equip` VALUES (1312, '布甲', 3, 132, 220, '<p>+90物理防御</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1312.jpg');
INSERT INTO `equip` VALUES (1313, '抗魔披风', 3, 132, 220, '<p>+90法术防御</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1313.jpg');
INSERT INTO `equip` VALUES (1314, '提神水晶', 3, 84, 140, '<p>+30 每5秒回复</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1314.jpg');
INSERT INTO `equip` VALUES (1321, '力量腰带', 3, 540, 900, '<p>+1000最大生命</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1321.jpg');
INSERT INTO `equip` VALUES (1322, '熔炼之心', 3, 540, 900, '<p>+700最大生命</p>', '<p>唯一被动-献祭：每秒对身边的敌军造成（60+英雄等级*2）点法术伤害</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1322.jpg');
INSERT INTO `equip` VALUES (1323, '神隐斗篷', 3, 612, 1020, '<p>+120法术防御<br>+700最大生命<br>+50每5秒回血</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1323.jpg');
INSERT INTO `equip` VALUES (1324, '雪山圆盾', 3, 540, 900, '<p>+10%减CD<br>+400最大法力<br>+110物理防御</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1324.jpg');
INSERT INTO `equip` VALUES (1325, '守护者之铠', 3, 438, 730, '<p>+210物理防御</p>', '<p>唯一被动-寒铁：受到攻击会减少攻击者15%攻击速度，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1325.jpg');
INSERT INTO `equip` VALUES (1327, '反伤刺甲', 3, 882, 1840, '<p>+40物理攻击<br>+420物理防御</p>', '<p>唯一被动-荆棘：受到物理伤害时，会将伤害量的20%以法术伤害的形式回敬给对方。自身每20点物理防御属性提升1%该伤害（最多+100%）。攻击者距离越远，这个伤害越低，最多在距离800时衰减至70%</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1327.jpg');
INSERT INTO `equip` VALUES (1328, '血魔之怒', 3, 1272, 2120, '<p>+20物理攻击<br>+1000最大生命</p>', '<p><p>唯一被动-血怒：生命值低于30%时获得血怒，增加80点攻击，并获得最大生命值30%的护盾，持续8秒，这个效果有90秒CD</p></p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1328.jpg');
INSERT INTO `equip` VALUES (1331, '红莲斗篷', 3, 1098, 1830, '<p>+240物理防御<br>+1000最大生命</p>', '<p>唯一被动-献祭：每秒对身边300范围内的敌人造成使用者最大生命值2%的法术伤害</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1331.jpg');
INSERT INTO `equip` VALUES (1332, '霸者重装', 3, 1422, 2070, '<p>+2000最大生命</p>', '<p>唯一被动-复苏：脱离战斗后每秒回复3%最大生命值</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1332.jpg');
INSERT INTO `equip` VALUES (1333, '不祥征兆', 3, 1308, 2180, '<p>+270物理防御<br>+1200最大生命</p>', '<p>唯一被动-寒铁：受到攻击会减少攻击者30%攻击速度与15%移动速度，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1333.jpg');
INSERT INTO `equip` VALUES (1334, '不死鸟之眼', 3, 1260, 2100, '<p>+100每5秒回血<br>+240法术防御<br>+1200最大生命</p>', '<p>唯一被动-血统：每损失10%生命值，受到的所有治疗效果会额外增加6%。</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1334.jpg');
INSERT INTO `equip` VALUES (1335, '魔女斗篷', 3, 1272, 2120, '<p>+360法术防御<br>+1000最大生命</p>', '<p>唯一被动-迷雾：脱战3秒后获得一个吸收（200+英雄等级*120）点法术伤害的护盾</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1335.jpg');
INSERT INTO `equip` VALUES (1336, '极寒风暴', 3, 1260, 2100, '<p>+20%冷却缩减<br>+500最大法力<br>+360物理防御</p>', '<p>唯一被动-冰心：受到单次伤害超过当前生命值10%时触发寒冰冲击，对周围敌人造成（50+英雄等级*10）点法术伤害并降低其30%攻击和移动速度，持续2秒，这个效果有2秒内置CD</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1336.jpg');
INSERT INTO `equip` VALUES (1337, '贤者的庇护', 3, 1248, 2080, '<p>+140物理防御<br>+140法术防御</p>', '<p>唯一被动-复生：死亡后2秒原地复活，并获得（2000+英雄等级*100）点生命值，冷却时间：150秒。这个效果每局游戏只能触发2次</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1337.jpg');
INSERT INTO `equip` VALUES (1338, '暴烈之甲', 3, 1092, 1950, '<p>+220物理防御<br>+1000最大生命</p>', '<p>唯一被动-无畏：每次受到伤害后，自身造成的所有伤害提升2%并增加2%的移速，这个效果最高可以叠加5层，最多提升10%的伤害输出和10%移速，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1338.jpg');
INSERT INTO `equip` VALUES (1411, '神速之靴', 4, 150, 250, '<p>唯一被动：+30移动速度</p>', '<p>所有鞋类装备的移速加成效果不叠加</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1411.jpg');
INSERT INTO `equip` VALUES (1421, '影忍之足', 4, 414, 710, '<p>+110物理防御</p>', '<p>唯一被动：+60移动速度（所有鞋类装备的移速加成效果不叠加）<br>唯一被动：减少15%受到普通攻击的伤害</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1421.jpg');
INSERT INTO `equip` VALUES (1422, '抵抗之靴', 4, 414, 710, '<p>+110法术防御</p>', '<p>唯一被动：+60移动速度（所有鞋类装备的移速加成效果不叠加）<br>唯一被动：+35%韧性</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1422.jpg');
INSERT INTO `equip` VALUES (1423, '冷静之靴', 4, 426, 710, '<p>+15%减CD</p>', '<p>唯一被动-静谧：所有英雄技能的冷却恢复速度加快3%~10%（随英雄等级成长）这个效果对剩余冷却时间小于5秒的技能不会生效。<br>唯一被动：+60移动速度（所有鞋类装备的移速加成效果不叠加）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1423.jpg');
INSERT INTO `equip` VALUES (1424, '秘法之靴', 4, 474, 710, '<p>唯一被动：+60移动速度（所有鞋类装备的移速加成效果不叠加）<br>唯一被动：+75法术穿透</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1424.jpg');
INSERT INTO `equip` VALUES (1425, '急速战靴', 4, 426, 710, '<p>+30%攻速</p>', '<p>唯一被动：+60移动速度（所有鞋类装备的移速加成效果不叠加）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1425.jpg');
INSERT INTO `equip` VALUES (1426, '疾步之靴', 4, 378, 530, '<p>唯一被动-神行：脱离战斗后增加60移动速度<br>唯一被动：+60移动速度（所有鞋类装备的移速加成效果不叠加）</p>', NULL, 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1426.jpg');
INSERT INTO `equip` VALUES (1511, '狩猎宽刃', 5, 150, 250, '<p>（打野刀升级后将惩戒技能替换为寒冰惩戒：寒冰惩戒可对英雄使用，造成伤害和减速效果）<br>必须携带惩击才可够买</p>', '<p>必须被动唯一被动-打野：增加35%对野怪的伤害，击杀野怪获得经验提升20%</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1511.jpg');
INSERT INTO `equip` VALUES (1521, '游击弯刀', 5, 450, 750, '<p>必须携带惩击才可够买，获得寒冰惩击效果</p>', '<p>唯一被动-打野：增加45%对野怪的伤害，击杀野怪获得经验提升30%，击杀野怪获得的金币提升20%<br>唯一被动-磨砺：击杀野怪增加自身6点法术攻击，最多叠加20层</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1521.jpg');
INSERT INTO `equip` VALUES (1522, '巡守利斧', 5, 450, 750, '<p>必须携带惩击才可够买，获得寒冰惩击效果</p>', '<p>唯一被动-打野：增加45%对野怪的伤害，击杀野怪获得经验提升30%，击杀野怪获得的金币提升20%<br>唯一被动-磨砺：击杀野怪增加自身50点最大生命，最多叠加20层</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1522.jpg');
INSERT INTO `equip` VALUES (1523, '追击刀锋', 5, 450, 750, '<p>必须携带惩击才可够买，获得寒冰惩击效果</p>', '<p>唯一被动-打野：增加45%对野怪的伤害，击杀野怪获得经验提升30%，击杀野怪获得的金币提升20%<br>唯一被动-磨砺：击杀野怪增加自身2点物理攻击和0.5%冷却缩减，最多叠加20层</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1523.jpg');
INSERT INTO `equip` VALUES (1531, '符文大剑', 5, 894, 1490, '<p>+100法术攻击<br>+400最大法力<br>必须携带惩击才可够买，获得寒冰惩击效果</p>', '<p>唯一被动-强击：使用技能后，5秒内的下一次普通攻击附加50+0.5*法术攻击的法术伤害，冷却时间：2秒<br>唯一被动-打野：增加45%对野怪的伤害，击杀野怪获得经验提升30%，击杀野怪获得的金币提升20%<br>唯一被动-磨砺：击杀野怪增加自身6点法术攻击，最多叠加30层</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1531.jpg');
INSERT INTO `equip` VALUES (1532, '巨人之握', 5, 900, 1500, '<p>+800最大生命<br>必须携带惩击才可够买，获得寒冰惩击效果</p>', '<p>唯一被动-献祭：每秒对身边300范围内的敌人造成（30+英雄等级*6）点法术伤害<br>唯一被动-打野：增加45%对野怪的伤害，击杀野怪获得经验提升30%，击杀野怪获得的金币提升20%<br>唯一被动-磨砺：击杀野怪增加自身50点最大生命，最多叠加30层</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1532.jpg');
INSERT INTO `equip` VALUES (1533, '贪婪之噬', 5, 876, 1460, '<p>+40物理攻击<br>+15%攻击速度<br>+8%移速<br>必须携带惩击才可够买，获得寒冰惩击效果</p>', '<p>唯一被动-打野：增加45%对野怪的伤害，击杀野怪获得经验提升30%，击杀野怪获得的金币提升20%<br>唯一被动-磨砺：击杀野怪增加自身2点物理攻击和0.5%冷却缩减，最多叠加30层</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1533.jpg');
INSERT INTO `equip` VALUES (1701, '学识宝石', 7, 180, 300, '<p>+移动速度+5%</p>', '<p>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1701.jpg');
INSERT INTO `equip` VALUES (1711, '凤鸣指环', 7, 642, 1070, '<p>+移动速度+5%<br>+生命值500</p>', '<p>所有辅助装备的主动技能均为全队共享冷却时间<br>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一主动-鼓舞：45秒CD，为周围友方英雄增加30%攻击速度和10%冷却缩减，持续5秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1711.jpg');
INSERT INTO `equip` VALUES (1712, '风之轻语', 7, 606, 1010, '<p>+移动速度+5%<br>+生命值500</p>', '<p>所有辅助装备的主动技能均为全队共享冷却时间<br>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一主动-救援：60秒CD，立即为周围血量最低的友方英雄（包括自己）提供一个吸收（500+英雄等级*50）伤害的护盾，并提升其15%移动速度，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1712.jpg');
INSERT INTO `equip` VALUES (1713, '风灵纹章', 7, 708, 1180, '<p>+移动速度+5%<br>+生命值500</p>', '<p>所有辅助装备的主动技能均为全队共享冷却时间<br>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一主动-奔腾号令：60秒CD，增加周围所有友方英雄30%的移动速度，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1713.jpg');
INSERT INTO `equip` VALUES (1714, '鼓舞之盾', 7, 726, 1210, '<p>+移动速度+5%<br>+生命值500</p>', '<p>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一被动-军团：提升周围800范围友军(20+英雄等级*2)点物理攻击和(40+英雄等级*4)点法术攻击</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1714.jpg');
INSERT INTO `equip` VALUES (1721, '极影', 7, 1146, 1910, '<p>+移动速度+10%<br>+生命值1000</p>', '<p>所有辅助装备的主动技能均为全队共享冷却时间<br>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一主动-鼓舞：45秒CD，为周围友方英雄增加50%攻击速度和20%冷却缩减，持续5秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1721.jpg');
INSERT INTO `equip` VALUES (1722, '救赎之翼', 7, 1080, 1800, '<p>+移动速度+10%<br>+生命值1000</p>', '<p>所有辅助装备的主动技能均为全队共享冷却时间<br>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一主动-救援：60秒CD，立即为周围血量最低的友方英雄（包括自己）提供一个吸收（800+英雄等级*80）伤害的护盾，并提升其30%移动速度，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1722.jpg');
INSERT INTO `equip` VALUES (1723, '奔狼纹章', 7, 1254, 2090, '<p>+移动速度+10%<br>+生命值1000</p>', '<p>所有辅助装备的主动技能均为全队共享冷却时间<br>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一主动-奔腾号令：60秒CD，增加周围所有友方英雄50%的移动速度，持续3秒</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1723.jpg');
INSERT INTO `equip` VALUES (1724, '近卫荣耀', 7, 1206, 2010, '<p>+移动速度+10%<br>+生命值1000</p>', '<p>唯一被动-奉献：如果自己的经验或经济是己方最低，每3秒会额外获得5点经验或金币<br>唯一被动-军团：提升周围800范围友军(30+英雄等级*3)点物理攻击和(60+英雄等级*6)点法术攻击</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/1724.jpg');
INSERT INTO `equip` VALUES (11210, '制裁之刃', 1, 960, 1800, '<p>+100物理攻击<br>+10%物理吸血</p> \n', '<p>唯一被动-重伤：造成伤害使得目标的生命恢复效果减少50%，持续1.5秒（如果该伤害由普攻触发，则持续时间延长至3秒）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/11210.jpg');
INSERT INTO `equip` VALUES (11311, '纯净苍穹', 1, 1338, 2230, '<p>+40%攻击速度<br>+20%暴击率</p>', '<p>唯一被动-精准：普通攻击伤害提升35点，远程英雄使用时该效果翻倍。<br>\n唯一主动-驱散：90秒CD，受到的所有伤害降低50%，持续1.5秒，可以在被控制时使用。</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/11311.jpg');
INSERT INTO `equip` VALUES (12211, '梦魇之牙', 2, 1056, 2050, '<p>+240法术攻击<br>+5%移速</p>', '<p>唯一被动-重伤：造成伤害使得目标的生命恢复效果减少50%，持续1.5秒（如果该伤害由普攻触发，则持续时间延长至3秒）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/12211.jpg');
INSERT INTO `equip` VALUES (13310, '冰痕之握', 3, 1242, 2020, '<p>+800最大生命<br>+500最大法力<br>+10%冷却缩减<br>+200物理防御</p>', '<p>唯一被动-强击：使用技能后，5秒内的下一次普攻造成范围30%减速（远程英雄使用时减速效果衰减为20%）与（150+英雄等级*20）点物理伤害，这个效果有3秒的冷却时间</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/13310.jpg');
INSERT INTO `equip` VALUES (91040, '逐日之弓', 1, 890, 2100, '<p>攻击速度+25%\n<br>暴击+15%</p>', '<p>唯一被动-精准：普通攻击伤害提升35点，远程英雄使用时该效果翻倍。<br>\n唯一主动-逐日：增加自己150点普攻射程和40%移动速度，持续5秒，CD60秒（仅对远程英雄生效）</p>', 'http://game.gtimg.cn/images/yxzj/img201606/itemimg/91040.jpg');
