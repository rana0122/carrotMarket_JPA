package miniproject.carrotmarket1.repository.MySQL;

/*
@Repository
public class MySQLProductRepository implements ProductRepository {

    private final ProductDAO productDAO;

    @Autowired
    public MySQLProductRepository(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }


    //ID로 상품 상세 조회
    @Override
    public Product findById(Long productId) {
        return productDAO.findById(productId);
    }

    //게시글 저장
    @Override
    public void insertProduct(Product product) {
        productDAO.insertProduct(product);
    }

    //게시글 수정
    @Override
    public void updateProduct(Product product) {
        productDAO.updateProduct(product);
    }

    //반경내 게시글 조회
    @Override
    public Page<Product> findProductsWithinRadius(Double latitude, Double longitude, Double radiusKm,
                                                  Long categoryId, String status, String keyword, Pageable pageable) {
        List<Product> products = productDAO.findProductsWithinRadius(latitude, longitude, radiusKm, categoryId, status, keyword,
                                                                        pageable.getPageSize(), (int) pageable.getOffset());
        long totalCount = 0;
        if (!products.isEmpty()) {
            totalCount = products.get(0).getTotalCount();
        }else{
            return Page.empty();
        }

        return new PageImpl<>(products, pageable, totalCount);
    }

    //게시글 조회(로그인 안한 경우)
    @Override
    public Page<Product> findProductsByConditions(Long categoryId, String status, String keyword, Pageable pageable) {
        List<Product> products = productDAO.findProductsByConditions(categoryId, status, keyword,pageable.getPageSize(), (int) pageable.getOffset());
        long totalCount = 0;
        if (!products.isEmpty()) {
            totalCount = products.get(0).getTotalCount();
        }else{
            return Page.empty();
        }

        return new PageImpl<>(products, pageable, totalCount);
    }

    @Override
    public void updateReservationStatus(Long productId, String status) {
        productDAO.updateReservationStatus(productId,status);
    }

    @Override
    public List<Product> findByUserId(Long userId) {
        return productDAO.findByUserId(userId);
    }

    //게시글 삭제시 이미지 삭제
    @Override
    public void deleteById(Long id) {
        productDAO.deleteById(id);
    }


}*/
