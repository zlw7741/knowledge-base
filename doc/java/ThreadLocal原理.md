### 1.ThreadLocal原理
##### ThreadLocal.set逻辑：

- 1.获取当前线程
- 2.获取当前线程中的ThreadLocalMap
- 3.如果ThreadLocalMap不为空，set(当前ThreadLocal对象，value),key和value存ThreadLocalMap对象的Entry数组中
- 4.如果ThreadLocalMap为空，创建createMap
    - 1.t.threadLocals=new ThreadLocalMap(this, firstValue);
    - 2.ThreadLocalMap构造：初始Entry数组16个，下标=key.HashCode & (Entry数组容量-1),将当前ThreadLocal为key,值为value存入Entry数组，加载因子为容量的2/3
      
  (注意：object的hash是native接口，而ThreadLocal的hash实现是AtomicInteger.getAndAdd,是cas，每次创建ThreadLocal都累加魔数0x61c88647（1640531527），让哈希码能均匀的分布在2的N次方的数组里)
- 5.如果ThreadLocalMap不为空
  - 1.根据key的hash与容量位运算获取下标
    - 1.1 for循环，获取的Entry不为空，如果获取的Entry.ThreadLocal与传进来的ThreadLocal相等，覆盖原有value值，如果获取的Entry.ThreadLocal为空，清理脏Entry。跳出循环。
    - 1.2 如果获取的Entry为空，将对象放到当前Entry[]下标下
    - 1.3 如果不需要清理slot槽（Entry中的ThreadLocal有为空的情况，就需要清理槽位了），且容量大于2/3，进行扩容rehash()
    
    (cleanSomeSlots清理槽位其实就是将存储在ThreadLocalMap.Entry[]存的Entry置null)
    - 1.4 扩容rehash(),当容量达到（容量-容量/4）时，以2倍进行扩容resize()
      - 扩容流程：遍历Entry[]
        - 如果entry为空，entry的ThreadLocal为空，将value也置为null
        - 如果entry不为空，重新hash获取下标，如果下标的对象不为空，获取下一个下标，直到获取到的null的对象，存储当前对象。

##### ThreadLocal.get逻辑：
  - 1.获取当前线程，获取当前线程中ThreadLocalMap
  - 2.如果ThreadLocalMap为null,初始化一个key为当前hreadLocal，value为null的对象存ThreadLocalMap中
  - 3.如果ThreadLocalMap不为null，获取ThreadLocalMap.Entry返回。（map.getEntry(this);） 
  
    - map.getEntry(this);逻辑：根据ThreadLocal&nbsp;hash获取下标，根据下标获取Entry
      
    - 如果Entry不为空，且Entry跟传进来的Entry相等，返回Entry对象。
      
    - 否则，可能hash碰撞了
      - 获取Entry中的ThreadLocal，如果ThreadLocal等于传入的key,返回Entry，
      - 如果Entry等于null,说明这个对象的key不存在了，将Entry[]中的这个对象置null，并将置空的元素的下一个元素重新hash，直到获取的元素为空。
      - 否则获取下一个元素，直到获取到的元素key（ThreadLocal）值与传入的key值相等，返回Entry

解决hash冲突：
  - 1.hashMap采用链表地址法（链表法，就是将相同hash值的对象，组织成一个链表，放在hash值对应的槽位；）
  - 2.ThreadLocal采用开放地址法（开放地址法，是通过一个探测算法，当某个槽位已经被占据的情况下，继续查找下一个可以使用的槽位。）





