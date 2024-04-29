### 1.stream使用

```
// 1.根据对象里的某个字段转成map
Map(String,DecompDTO) regionMap = list.stream().collect(Collectors.toMap(DecompDTO::getRegionCode,e->e,(e1,e2) -> e2));

// 2.分组
Map<String,Integer> map = list.stream().collect(Collectors.groupingBy(RegionEntity:getRegion,Collectors.summingInt(RegionEntity::getRegionLevel)));

// 3.分组
Map<String,List<RegionEntity>> map = list.stream().collect(Collectors.groupingBy(RegionEntity::getRegion));

// 4.list转map
CollectionUtils.toMap(list,e -> e.getOrganizationCode(),e -> e.getAchievementValue());

// 5.根据查出来的数据，筛选集合对象中相同的数据和没有的数据
// 5.1存在的数据
List<ToolOwnerDO> doList = 数据；
存在的数据
List<ToolOwnerDO> list1 = doList.stream().filter(m -> doList.stream().map(d->d.getIdTool()).collect(Collectors.toList()).contains(m.getIdTool())).collect(Collectors.toList());
不存在的数据
List<ToolOwnerDO> list2 = doList.stream().filter(m -> !doList.stream().map(d->d.getIdTool()).collect(Collectors.toList()).contains(m.getIdTool())).collect(Collectors.toList());

// 6.将集合的字段取出作为一个集合
List<String> orgList = list.stream().map(Aa::getOrgCode).collect(Collectors.toList());

// 7.将集合中的两个元素组成map
Map<String,String> map = list.stream().filter(e->(StringUtils.isNotBlank(e.getBranchId()))).collect(Collectors.toMap(BranchInfoEntity::getBranchId,BranchInfoEntity::getBranchName));

// 8.取两个集合的交集
List<String> list = list1.stream().filter(list2::contains).collect(Collectors.toList());

// 9.List对象，根据对象的某字段去重
ArrayList<PersonInfoEntity> treeSet = list.stream().collect(Collector.collectingAndThen(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(e->e.getPersonNum()+e.getIdCardNum()))),ArrayList::new));

// 10.获取两个对象的补集(两个对象必须类型相同)
List<Bb> aList = list1.stream().filter(e->{
    if(CollectionUtils.isEmpty(list2)){
        for(Bb bb : list2){
            if(!e.getUserId().equals(bb.getUserId)){
                return false;
            }
        }
        return true;
    }
    return true;
}).collect(Collectors.toList());




```
